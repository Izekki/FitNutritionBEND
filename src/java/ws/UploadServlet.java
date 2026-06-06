package ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.mybatis.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

@WebServlet(name = "UploadServlet", urlPatterns = { "/uploads/*" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class UploadServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "C:/fitNutrition/uploads/";

    @Override
    public void init() throws ServletException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (created) {
                System.out.println("Directorios de carga creados exitosamente: " + UPLOAD_DIR);
            } else {
                System.err.println("No se pudieron crear los directorios de carga: " + UPLOAD_DIR);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Se valida que la ruta sea /upload
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || !pathInfo.equals("/upload")) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND,
                    "Recurso no encontrado. La ruta esperada es POST /uploads/upload");
            return;
        }

        Part filePart = null;
        try {
            filePart = request.getPart("file");
        } catch (Exception e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "No se pudo recuperar la parte 'file' de la solicitud multipart: " + e.getMessage());
            return;
        }

        String entityType = request.getParameter("entityType");
        String idStr = request.getParameter("id");

        if (filePart == null || entityType == null || idStr == null) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Parámetros requeridos faltantes: file (binario), entityType (medico|paciente), id (numérico)");
            return;
        }

        entityType = entityType.trim().toLowerCase();
        if (!"medico".equals(entityType) && !"paciente".equals(entityType)) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "El parámetro 'entityType' debe ser 'medico' o 'paciente'");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "El parámetro 'id' debe ser un número entero válido");
            return;
        }

        String submittedFileName = getSubmittedFileName(filePart);
        if (submittedFileName == null || submittedFileName.trim().isEmpty()) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo de carga inválido");
            return;
        }

        String extension = "";
        int dotIndex = submittedFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = submittedFileName.substring(dotIndex).toLowerCase();
        }

        if (!".jpg".equals(extension) && !".jpeg".equals(extension) && !".png".equals(extension)) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST,
                    "Formato de archivo no soportado. Debe ser JPG, JPEG o PNG");
            return;
        }

        // Generar un nombre único para evitar colisiones
        String uniqueFileName = entityType + "_" + id + "_" + System.currentTimeMillis() + extension;
        File storeFile = new File(UPLOAD_DIR, uniqueFileName);

        // Guardar físicamente el archivo en el servidor
        try (InputStream input = filePart.getInputStream();
                OutputStream output = new FileOutputStream(storeFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se pudo guardar físicamente el archivo en el servidor: " + e.getMessage());
            return;
        }

        // Registrar en base de datos
        boolean dbUpdated = false;
        SqlSession conexionBD = MybatisUtil.getSession();
        if (conexionBD != null) {
            try {
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("fotografia", uniqueFileName);
                int filas = 0;
                if ("medico".equals(entityType)) {
                    parametros.put("idMedico", id);
                    filas = conexionBD.update("medico.actualizarFotografia", parametros);
                } else {
                    parametros.put("idPaciente", id);
                    filas = conexionBD.update("paciente.actualizarFotografia", parametros);
                }

                if (filas > 0) {
                    conexionBD.commit();
                    dbUpdated = true;
                } else {
                    conexionBD.rollback();
                }
            } catch (Exception e) {
                conexionBD.rollback();
                // Si la base de datos falla, se borra el archivo físico para consistencia
                if (storeFile.exists()) {
                    storeFile.delete();
                }
                sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error en la transacción de base de datos, transacción abortada: " + e.getMessage());
                return;
            } finally {
                conexionBD.close();
            }
        } else {
            if (storeFile.exists()) {
                storeFile.delete();
            }
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se pudo establecer la sesión con la base de datos");
            return;
        }

        if (!dbUpdated) {
            if (storeFile.exists()) {
                storeFile.delete();
            }
            sendError(response, HttpServletResponse.SC_NOT_FOUND,
                    "No se encontró ningún registro para la entidad '" + entityType + "' con ID: " + id);
            return;
        }

        sendSuccess(response, "Fotografía guardada exitosamente", uniqueFileName);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo faltante en la URL");
            return;
        }

        if (!pathInfo.startsWith("/ver/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Ruta inválida. Formato esperado: GET /uploads/ver/{fileName}");
            return;
        }

        String fileName = pathInfo.substring(5); // Retirar "/ver/"
        if (fileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo vacío");
            return;
        }

        // Sanitización contra Directory Traversal
        if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Petición inválida y rechazada por motivos de seguridad");
            return;
        }

        File file = new File(UPLOAD_DIR, fileName);
        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "El archivo de imagen solicitado no existe");
            return;
        }

        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            if (file.getName().endsWith(".png")) {
                contentType = "image/png";
            } else {
                contentType = "image/jpeg";
            }
        }

        response.setContentType(contentType);
        response.setContentLength((int) file.length());

        try (InputStream in = new FileInputStream(file);
                OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(String.format("{\"error\":true,\"mensaje\":\"%s\"}", message.replace("\"", "\\\"")));
            out.flush();
        }
    }

    private void sendSuccess(HttpServletResponse response, String message, String fileName) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(String.format("{\"error\":false,\"mensaje\":\"%s\",\"fileName\":\"%s\"}",
                    message.replace("\"", "\\\""), fileName.replace("\"", "\\\"")));
            out.flush();
        }
    }

    private String getSubmittedFileName(Part filePart) {
        String contentDisposition = filePart.getHeader("content-disposition");
        if (contentDisposition == null) {
            return null;
        }

        for (String value : contentDisposition.split(";")) {
            String trimmedValue = value.trim();
            if (trimmedValue.startsWith("filename")) {
                String fileName = trimmedValue.substring(trimmedValue.indexOf('=') + 1).trim();
                if (fileName.startsWith("\"") && fileName.endsWith("\"") && fileName.length() >= 2) {
                    fileName = fileName.substring(1, fileName.length() - 1);
                }
                return new File(fileName).getName();
            }
        }

        return null;
    }
}
