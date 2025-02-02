<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="ro">

<head>
  <!-- Required meta tags -->
 <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>InoviSmartApp View Location</title>
  <!-- base:css -->
  <link rel="stylesheet" href="adminresources/vendors/typicons/typicons.css">
  <link rel="stylesheet" href="adminresources/vendors/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- inject:css -->
  <link rel="stylesheet" href="adminresources/css/vertical-layout-light/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="adminresources/images/logo-inovi2.png" />
  <%@ page import="java.util.List" %>
  <%@ page import="org.eurovending.controller.LoginController" %>
  <%@ page import = "java.io.*,java.util.*" %>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>
<%
//allow access only if session exists
String user = null;
if(session.getAttribute("email") == null){
	response.sendRedirect("LoginOut.htm");
}else user = (String) session.getAttribute("email");
if(LoginController.getMyEmail().equals(user)){
String userName = null;
String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("email")) userName = cookie.getValue();
	if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
}
}else{
	sessionID = session.getId();
}
 }else{
	session.setAttribute("email", null);
	response.sendRedirect("LoginOut.htm");
}
//Set refresh, autoload time as 60 seconds
response.setIntHeader("Refresh", 60);
%>


  <%@page import="java.io.OutputStream"%>
    <!-- modalJSP -->
 

    <div class="container-scroller">
<!-- NavBar -->
    <!-- partial:partials/_navbar.html -->
    <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div class="navbar-brand-wrapper d-flex justify-content-center">
          <div class="navbar-brand-inner-wrapper d-flex justify-content-between align-items-center w-100">
            <a class="navbar-brand brand-logo-mini" href="LoginOut.htm"><img src="adminresources/images/logo-inovi2.png" alt="logo"/></a>
            <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
              <span class="typcn typcn-th-menu"></span>
            </button>
          </div>
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
          <ul class="navbar-nav mr-lg-2">
            <li class="nav-item nav-profile dropdown">
              
              <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
               
                <a class="dropdown-item" href="LoginOut.htm">
                  <i class="typcn typcn-eject text-primary"></i>
                  Logout
                </a>
              </div>
            </li>
            <li class="nav-item nav-user-status dropdown">
                <p id="day"><p> / </p></p><p id="month"><p> / </p></p><p id="year">
            </li>
            <li>
             <img src="adminresources/images/logo-inovi2.png" alt="logo"/>
             </li>
             <li>
             <a class="nav-link" href="#" data-toggle="dropdown" id="profileDropdown">
           
                <span class="nav-profile-name"><c:out value="${user}"></c:out></span>
              </a>
             </li>
          </ul>
         
          <ul class="navbar-nav navbar-nav-right">
           
           
             <script type="text/javascript">
              var days = ['Duminica', 'Luni', 'Marti', 'Miercuri', 'Joi', 'Vineri', 'Sambata'];
       var d = new Date();
       var dayName =d.getDate()+" " + days[d.getDay()];
       var year = new Date().getFullYear();
       var monthName = d.toLocaleString('default', { month: 'long' });
       document.getElementById("day").innerHTML = dayName;
       document.getElementById("month").innerHTML = monthName;
       document.getElementById("year").innerHTML = year;
       </script>        
          </ul>
          <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
            <span class="typcn typcn-th-menu"></span>
          </button>
        </div>
      </nav>
      <!-- partial -->  
  <!-- endNavbar -->
	<!-- NavBar -->
	 <div class="container-fluid page-body-wrapper">
  <!-- partial:partials/_settings-panel.html -->
        <div class="theme-setting-wrapper">
            <div id="settings-trigger"><i class="typcn typcn-cog-outline"></i></div>
            <div id="theme-settings" class="settings-panel">
              <i class="settings-close typcn typcn-times"></i>
              <p class="settings-heading">SIDEBAR SKINS</p>
              <div class="sidebar-bg-options selected" id="sidebar-light-theme"><div class="img-ss rounded-circle bg-light border mr-3"></div>Light</div>
              <div class="sidebar-bg-options" id="sidebar-dark-theme"><div class="img-ss rounded-circle bg-dark border mr-3"></div>Dark</div>
              <p class="settings-heading mt-2">HEADER SKINS</p>
              <div class="color-tiles mx-0 px-4">
                <div class="tiles success"></div>
                <div class="tiles warning"></div>
                <div class="tiles danger"></div>
                <div class="tiles info"></div>
                <div class="tiles dark"></div>
                <div class="tiles default"></div>
              </div>
            </div>
          </div>
 
 <!-- partial:partials/_sidebar.html -->
          <nav class="sidebar sidebar-offcanvas" id="sidebar">
            <ul class="nav">
              <li class="nav-item">
                <a class="nav-link" data-toggle="collapse" href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
                  <i class="typcn typcn-document-text menu-icon"></i>
                  <span class="menu-title">Contul Meu</span>
                  <i class="menu-arrow"></i>
                </a>
                <div class="collapse" id="ui-basic">
                  <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="<c:url value="view-company.htm"></c:url>">Profil Societate</a></li>                  
                    <li class="nav-item"> <a class="nav-link" href="<c:url value="userhome.htm"></c:url>">ListaLocatii</a></li>
                     
                </ul>
                </div>
              </li>
              <li class="nav-item">
                <a class="nav-link"  href="LoginOut.htm" aria-expanded="false" aria-controls="auth">
                  <i class="typcn typcn-user-add-outline menu-icon"></i>
                  <span class="menu-title">Log Out</span>
                  <i class="menu-arrow"></i>
                </a>
               
              </li>  
            </ul>
          </nav>
          <!-- partial -->
  <!-- endNavbar -->
     


      
      
      <div class="main-panel">
      <!-- Content wrapper -->
      <div class="content-wrapper">
          <div class="row">
            <div class="col-lg-12 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
                  <h4 class="card-title"> Location <code>List</code></h4>
                  <div class="table-responsive">
                    <table class="table table-hover">
                    <thead>
<tr>
<th>Id</th>
<th>Statia</th>
<th>Adresa</th>
<th>M.A.C</th>
<th>Temperatura</th>
<th><p>Umiditate</p><p>Aer %</p></th>
<th><p>Nivel Lumina</p><p> Lux</p></th>
<th>
<p>Poluare</p>
<p>ug/m3</p>
</th>
<th>
<p>Grad Zgomot</p>
<p>Decibeli</p>
</th>
</tr>
</thead>
                      <tbody>
<tr>

<td><c:out value="${getLocation.id}"></c:out></td>

<td ><a class="button badge badge-success" href ="#"><c:out value="${getLocation.locationName}"></c:out></a></td>
<td><c:out value="${getLocation.locationAdress}"></c:out></td>
<td><c:out value="${getLocation.interfaceParam.macAddress}"></c:out></td>
<td><c:out value="${getLocation.interfaceParam.temperature}"></c:out></td>
<td><c:out value="${getLocation.interfaceParam.humidity}"></c:out></td>
<td><c:out value="${getLocation.interfaceParam.light}"></c:out></td>
<td><c:out value="${getLocation.interfaceParam.pollutionCo}"></c:out></td>
<td><c:out value="${getLocation.interfaceParam.pollutionGases}"></c:out></td>

<td>
<br>
</td>

<td>							                 
                         </td>
                        </tr>       
                      </tbody>
                    </table>
                  </div>
              </div>
            </div>

          </div>
        </div>
        <!-- content-wrapper ends -->
 <div>
 		     <!-- Content wrapper -->
      <div class="content-wrapper">
          <div class="row">
            <div class="col-lg-12 grid-margin stretch-card">
              <div class="card">
                <div class="card-body">
						<form style="text-align: center;" action="list-paramLocationUser.htm">
                           <label for="birthday">Alege Data:</label>
                           <input type="hidden" id="id" name="id" value="<c:out value="${getLocation.id}"></c:out>">
                           <br>
                             <input type="date" id="date" name="date">
            						<br>
            					<label for="resolutionTable">Rezolutie Grafic</label>
            					<br>
							<select id="resolutionTable" name="resolutionTable">
 							 <option value="10">X10</option>
 								<option value="20">X20</option>
  									<option value="50">X50</option>
  										<option value="100">X100</option>
											</select>
                               <input style="text-align: center;" type="submit" value="Submit"
                                      style="background-color: green; color: #fff; 
                                      padding: 10px 20px; border-radius: 5px; 
                                       border: none; cursor: pointer;">
                                      </form> 
                                      <canvas id="myCharte"></canvas>
		</div>
		</div>
		</div>
		</div>
		</div>
		
		
		
	<script>
		const ctx = document.getElementById('myCharte');
		//Obține valorile din modelul returnat de controller
		  var xValues = [<c:forEach items="${paramDataList}" var="parametre">
		 '${parametre.time}'<c:if test="${!loop.last}">,</c:if>
		 </c:forEach>];
		//Obține valorile din modelul returnat de controller
		 // const yValues = [55, 49, 44, 24, 15];
		 var temperatureValues = [<c:forEach items="${paramDataList}" var="parametre">
		 '${parametre.temperature}'<c:if test="${!loop.last}">,</c:if>
		 </c:forEach>];
		 var humidityValues = [<c:forEach items="${paramDataList}" var="parametre">
		 '${parametre.humidity}'<c:if test="${!loop.last}">,</c:if>
		 </c:forEach>];
		 var pollutionCoValues = [<c:forEach items="${paramDataList}" var="parametre">
		 '${parametre.pollutionCo}'<c:if test="${!loop.last}">,</c:if>
		 </c:forEach>];
		 var soundValues = [<c:forEach items="${paramDataList}" var="parametre">
		 '${parametre.pollutionSound}'<c:if test="${!loop.last}">,</c:if>
		 </c:forEach>];
		 var lightValues = [<c:forEach items="${paramDataList}" var="parametre">
		 '${parametre.light}'<c:if test="${!loop.last}">,</c:if>
		 </c:forEach>];	
		new Chart(ctx, {
			type: 'line',
			data: {
				labels: xValues,
				datasets: [{
					label: 'Temperatura',
					data:  temperatureValues,
					borderWidth: 3,
					backgroundColor: [ 'blue'],
					borderColor: ['lightgreen'],
				},
				{
					label: 'Umiditate',
					data:  humidityValues,
					borderWidth: 3,
					backgroundColor: ['lightgreen'],
					borderColor: ['blue'],
				},{
					label: 'PoluareGaze',
					data:  pollutionCoValues,
					borderWidth: 3,
					backgroundColor: ['orange'],
					borderColor: ['red'],
				},{
					label: 'NivelZgomot',
					data:  soundValues,
					borderWidth: 3,
					backgroundColor: ['brown'],
					borderColor: ['brown'],
				},{
					label: 'NivelLumina X 100',
					data:  lightValues,
					borderWidth: 3,
					backgroundColor: ['##48f542'],
					borderColor: ['#f5f542'],
				}
				]
			},
			options: {
				aspectRatio: 2,
				plugins: {
					legend: {
						display: true,
						position: 'left',
						reverse: true, // Reverse order of legend labels 
						title: {
							display: true,
							text: 'The weather today',
							font: {
								size: 14,
								weight: 'bold'
							},
							padding: 20,
							color: 'green'
						}
					}
				}
			}
		});
	</script>	
        <!-- partial:../../partials/_footer.html -->
        <footer class="footer">
            <div class="card">
                <div class="card-body">
                
                    <div class="d-sm-flex justify-content-center justify-content-sm-between">
                    <div class="container copyright">
                       <p >&copy; Copyrights <span id="year1"></span> <a href="#">InoviSmartAPP</a>. All rights reserved.</p>
                           </div>
                    </div>
                </div>    
            </div>        
        </footer>
        <!-- partial -->
      </div>
      <!-- main-panel ends -->
    </div>
    <!-- page-body-wrapper ends -->
  </div>
  <script type="text/javascript">
  document.getElementById("year1").innerHTML = new Date().getFullYear();
  </script>
  <!-- container-scroller -->
  <!-- base:js -->
  <script src="adminresources/vendors/js/vendor.bundle.base.js"></script>
  <!-- endinject -->
  <!-- Plugin js for this page-->
  <!-- End plugin js for this page-->
  <!-- inject:js -->
  <script src="adminresources/js/off-canvas.js"></script>
  <script src="adminresources/js/hoverable-collapse.js"></script>
  <script src="adminresources/js/template.js"></script>
  <script src="adminresources/js/settings.js"></script>
  <script src="adminresources/js/todolist.js"></script>
  <!-- endinject -->
  <!-- Custom js for this page-->
  <!-- End custom js for this page-->
</body>
</html>