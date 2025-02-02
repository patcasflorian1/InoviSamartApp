<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
       <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="ro" prefix="og: http://ogp.me/ns#" class="translated-ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>Modal Location</title>


<style>
* {
  box-sizing: border-box;
}

input[type=text], select, textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
}

label {
  padding: 12px 12px 12px 0;
  display: inline-block;
}

input[type=submit] {
  background-color: #04AA6D;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  float: right;
}

input[type=submit]:hover {
  background-color: #45a049;
}

.container {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}

.col-25 {
  float: left;
  width: 25%;
  margin-top: 6px;
}

.col-75 {
  float: left;
  width: 75%;
  margin-top: 6px;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .col-25, .col-75, input[type=submit] {
    width: 100%;
    margin-top: 0;
  }
}
</style>

</head>
<body>
<%@page import="java.io.OutputStream"%>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>

<!-- Modal Add Location -->
  <div class="modal fade" id="AddNewLocation" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Add Location</h4>
       
       <c:if test="${not empty model.msgError}">
        <a href="javascript:window. history. back();">Back</a>
        </c:if>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <form:form action="addLocation.htm" commandname="locname" metod="post">
        <h6>Location Name</h6>
         <input type="text" name="locationName" id="locationName" value="" required />
          <br>
          <h6>Location Address</h6>
         <input type="text" name="locationAdress"  id="locationAdress" value="" required/>
          <br>
        <!-- Modal footer -->
        <div class="modal-footer">     
        <input type="submit"  class="buttons btn btn-info add-new" value="Save"/>
        </div>
         </form:form>
          <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        </div>
         </div>
         </div>
      </div>
      <!-- Modal edit Location -->
  <div class="modal fade" id="EditLocation" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Edit Location</h4>
        <span class="nav-profile-name"><c:out value = "${ model.msgError}" ></c:out></span>
       <c:if test="${not empty model.msgError}">
        <a href="javascript:window. history. back();">Back</a>
        </c:if>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <form:form action="editlocation.htm" metod="post">
         <input type="hidden"  name="id" id="id" value=""/>
          <br>      
         <span id="wrong"></span>
          <br>
          <h6>Denumire Locatie</h6>
         <input type="text" name="locationName" id="locationName" value="" />
          <br>
          <h6>Adresa Locatie</h6>
         <input type="text" name="locationAdress" id="locationAdress" value="" />
        <!-- Modal footer -->
        <div class="modal-footer"> 
            <input type="submit"  class="buttons btn btn-info add-new" value="Save"/>
        <button type="button" class="button badge badge-danger" data-dismiss="modal">Close</button>
        </div>
         </form:form>
          
        </div>
         </div>
         </div>
      </div>
 
      <!-- Script for modal Edit Location -->
 <script type="text/javascript">
$(document).on("click", ".button", function editLocation () {
	 var id = $(this).data('id');
     $(".modal-body #id").val(id);
     var locName = $(this).data('locname');
     $(".modal-body #locationName").val(locName);
     var locAddress = $(this).data('locationadress');
     $(".modal-body #locationAdress").val( locAddress);
});
</script>
      
<!-- End EditLocation Modal -->
<!-- Modal delete Location -->
  <div class="modal fade" id="deleteLocation" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Sterge Locatia</h4>
        
        <span class="nav-profile-name"><c:out value = "${ model.msgError}" ></c:out></span>
       <c:if test="${not empty model.msgError}">
        <a href="javascript:window. history. back();">Back</a>
        </c:if>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <p style="color:red;">Atentie aceasta operatiune este ireversibila!!!</p>
        <form:form action="deletelocation.htm" metod="post">
         <input type="hidden"  name="id" id="id" value=""/>
          <br>      
         <span id="wrong"></span>
          <br>
          <h6>Denumire Locatie</h6>
         <input type="text" name="locationName" id="locationName" value="" />
          <br>
        <!-- Modal footer -->
        <div class="modal-footer"> 
            <input type="submit"  class="buttons btn btn-info add-new" value="Delete"/>
        <button type="button" class="button badge badge-danger" data-dismiss="modal">Close</button>
        </div>
         </form:form>
          
        </div>
         </div>
         </div>
      </div>
 
      <!-- Script for modal Edit Location -->
 <script type="text/javascript">
$(document).on("click", ".button", function deleteLocation () {
	 var id = $(this).data('id');
     $(".modal-body #id").val(id);
     var locName = $(this).data('locname');
     $(".modal-body #locationName").val(locName);
});
</script>
      
<!-- End deleteLocation Modal -->
<!-- Modal delete Location -->
  <div class="modal fade" id="deleteInterface" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Sterge Interfata</h4>
        
        <span class="nav-profile-name"><c:out value = "${ model.msgError}" ></c:out></span>
       <c:if test="${not empty model.msgError}">
        <a href="javascript:window. history. back();">Back</a>
        </c:if>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <p style="color:red;">Atentie aceasta operatiune este ireversibila!!!</p>
        <form:form action="deleteInterface.htm" metod="post">
         <input type="hidden"  name="idInterface" id="idInterface" value=""/>
          <br>      
         <span id="wrong"></span>
          <br>
          <h6>Denumire Locatie</h6>
         <input type="text" name="macAddress" id="macAddress" value="" />
          <br>
        <!-- Modal footer -->
        <div class="modal-footer"> 
            <input type="submit"  class="buttons btn btn-info add-new" value="Delete"/>
        <button type="button" class="button badge badge-danger" data-dismiss="modal">Close</button>
        </div>
         </form:form>
          
        </div>
         </div>
         </div>
      </div>
 
      <!-- Script for modal Edit Location -->
 <script type="text/javascript">
$(document).on("click", ".button", function deleteInterface () {
	 var id = $(this).data('idinterface');
     $(".modal-body #idInterface").val(id);
     var macAddress = $(this).data('mac');
     $(".modal-body #macAddress").val(macAddress);
});
</script>
      
<!-- End deleteLocation Modal -->

</body>
</html>