<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>InoviSmartTechnologies | Registration Page</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta name="viewport" content="width=device-width, initial-scale=1">

   <!-- base:css -->
  <link rel="stylesheet" href="adminresources/vendors/typicons/typicons.css">
  <link rel="stylesheet" href="adminresources/vendors/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- plugin css for this page -->
  <!-- End plugin css for this page -->
  <!-- inject:css -->
  <link rel="stylesheet" href="adminresources/css/vertical-layout-light/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="adminresources/images/logo-inovi.png" />
  <style type="text/css">
  #bg-video {
  position: absolute;
  z-index: -1;
  top: 0;
  left: 0;
  width: 100%;
  mask-image: linear-gradient(to top, transparent 0%, black 50%, transparent 100%);
  user-select: none;
  pointer-events: none;
  filter: grayscale(0.8);
  aspect-ratio: 16/9;
}
  </style>
</head>
<body >
<iframe id=bg-video
      src="https://www.youtube.com/embed/a4Wd7r0Ku3c?playlist=a4Wd7r0Ku3c&autoplay=1&mute=1&controls=0&loop=1&end=59"
      frameBorder=0 allowFullScreen allow=autoplay /></iframe>
      
    <p style ="color:red"><c:out value="${model.mesaj}"></c:out></p>

				<div class="brand-logo">
                <img src="adminresources/images/logo-inovi.png" alt="logo">
              </div>
        <div class="row w-100 mx-0">
          <div class="col-lg-4 mx-auto">
            <div class="auth-form-light text-left py-5 px-4 px-sm-5">
              
              <h6 class="font-weight-light">Sign in to continue.</h6>
              <form action="loginAdmin.htm" method="post">
                <div class="form-group">
                  <input type="email" class="form-control form-control-lg" id="email" name="email" placeholder="Username">
                </div>
                <div class="form-group">
                  <input type="password" class="form-control form-control-lg" id="password" name="password" placeholder="Password">
                </div>
                <div class="mt-3">
                 <button class="btn btn-block btn-primary btn-lg font-weight-medium auth-form-btn" type="submit">SIGN IN</button>
                </div>
              </form>
            </div>
          </div>
        </div>

  <!-- base:js -->
  <script src="adminresources/vendors/js/vendor.bundle.base.js"></script>
  <!-- endinject -->
  <!-- inject:js -->
  <script src="adminresources/js/off-canvas.js"></script>
  <script src="adminresources/js/hoverable-collapse.js"></script>
  <script src="adminresources/js/template.js"></script>
  <script src="adminresources/js/settings.js"></script>
  <script src="adminresources/js/todolist.js"></script>
  <!-- endinject -->
</body>

</html>

