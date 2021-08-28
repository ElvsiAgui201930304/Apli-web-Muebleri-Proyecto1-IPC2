<%-- 
    Document   : Info-Piezas
    Created on : 22/08/2021, 17:13:46
    Author     : elvis_agui
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">

        <title>JSP Page</title>
    </head>
    <body>
        <div class="d-flex"> 
            <div class="card-body" style="background-color:#20c997 ">
                <h3 class="text-center">INFORMACION GENERAL DE PIEZAS</h3>
            </div>
        </div>
        <div class="card-body" style="background-color:activecaption ">
            <div class="card-body"> 
                <a class="btn btn-warning" href="Controlador?menu=infoPiezas&accion=ordenAsen">Ordenar Asendente</a>
                <a class="btn btn-warning" href="Controlador?menu=infoPiezas&accion=ordenDes">Ordenar Desendente</a>
                <br>
                <br>
                <table border="10" class="table table-dark table-hover text-center">
                    <thead>
                        <tr>
                            <th>NOMBRE</th>
                            <th>CANTIDAD</th>
                            <th>COSTO</th>
                        </tr>
                    </thead>
                    <tbody> 
                        <c:forEach var="piez" items="${piezas}">
                            <tr>
                                <td>${piez.getNombre()}</td>
                                <td>${piez.getCantidad()}</td>
                                <td>Q ${piez.getCosto()}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js" integrity="sha384-eMNCOe7tC1doHpGoWe/6oMVemdAVTMs2xqW4mwXrXsW0L84Iytr2wi5v2QjrP/xp" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.min.js" integrity="sha384-cn7l7gDp0eyniUwwAZgrzD06kc/tftFf19TOAs2zVinnD/C7E91j9yyk5//jjpt/" crossorigin="anonymous"></script>

    </body>
</html>
