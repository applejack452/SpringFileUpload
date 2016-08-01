<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" name="viewport"
       content="width=device-width, initial-scale=1">
<title>Spring MVC + Dropzone.js Example</title>

<link rel="stylesheet" type="text/css"
       href='<c:url value="bootstrap/css/bootstrap.min.css"/>'>
<link rel="stylesheet" type="text/css"
       href='<c:url value="bootstrap/css/bootstrap-dialog.min.css"/>'>
<link rel="stylesheet" type="text/css"
       href='<c:url value="css/style.css"/>'>
<script src="js/jquery-2.1.1.js"></script>
<!-- <script>
	function getFileFunction(file_name){
		console.log(file_name);
		var data = {"stored_file_name":file_name};
		
		$.ajax({
			type: "POST", 
			url: "fileDownload",
			//dataType: "text",			
			contentType: "application/json",
    		data:JSON.stringify(data),
			success: function(data){
				document.write(data);
			},
			error: function(){
				alert("Failed....");
			}
		});
	}
</script> -->
<script>
	// 파일 다운로드 or 삭제
	function fileFunction(file_name, type){
		var method = "post";
		var action = null;
		
		if(type == "download")
			action = "fileDownload";
		else if(type == "delete")
			action = "fileDelete";
		
		var form = document.createElement("form");
		form.setAttribute("method", method);
		form.setAttribute("action", action);
		
		var hiddenField = document.createElement("input");
		hiddenField.setAttribute("type","hidden");
		hiddenField.setAttribute("name", "fileName");
		hiddenField.setAttribute("value",file_name);
		form.appendChild(hiddenField);
		
		document.body.appendChild(form);
		form.submit();
		document.body.removeChild(form);
	}
	
	// 다중 파일 다운로드(파폭만 가능) or 삭제
	function multiFileFunction(action){
		
		var method = "post";
		
		var form = document.createElement("form");
		form.setAttribute("method", method);
		form.setAttribute("action", action);
		var count = 0;
		$('input:checkbox[name="checkFile"]').each(function() {
			if(this.checked){//checked 처리된 항목의 값
				
				var hiddenField = document.createElement("input");
				hiddenField.setAttribute("type","hidden");
				hiddenField.setAttribute("name", "fileName"+count);
				hiddenField.setAttribute("value",this.value);
				form.appendChild(hiddenField);
				
				count++;
			}
		 });
		var hiddenField = document.createElement("input");
		hiddenField.setAttribute("type","hidden");
		hiddenField.setAttribute("name", "fileCount");
		hiddenField.setAttribute("value",count);
		form.appendChild(hiddenField);
		document.body.appendChild(form);
		form.submit();
		document.body.removeChild(form);	
	}
	
</script>
</head>
<body>
       <div class="container">
              <div class="panel panel-default">
                    
                     <div class="panel-heading text-center">
                           <h3>Spring MVC + Dropzone.js Example</h3>
                     </div>
                    
                     <div class="panel-body">

                           <a class="btn btn-primary" href="${pageContext.request.contextPath}">
                                  <span class="glyphicon glyphicon-chevron-left"></span> Go Back
                           </a>
                           <br>
                           <h4>List of All Uploaded Files</h4>
                           
                     </div>
                     <table class="table table-hover table-condensed">
                           <thead>
                                  <tr>
                                         <th width="5%">S.N</th>
                                         <th width="40%">File Name</th>
                                         <th width="20%">File Type</th>
                                         <th width="15%">File Size</th>
                                         <th width="10%">Actions</th>
                                         <th width="10%"></th>
                                  </tr>
                           </thead>
                           <tbody>
                                  <c:forEach items="${fileList}" var="dataFile" varStatus="loopCounter">
                                         <tr>
                                                <td><input type="checkbox" name="checkFile" value="${dataFile.stored_file_name}">&nbsp;<c:out value="${loopCounter.count}" /></td>
                                                <td><c:out value="${dataFile.original_file_name}" /></td>
                                                <td><c:out value="${dataFile.file_type}" /></td>                                               
                                                <td><c:out value="${dataFile.file_size}"/></td> 
                                               <%-- 	<c:set var ="file_size" value="${dataFile.file_size}"/>
                                               	<fmt:parseNumber var="size" type="number" value="${file_size}"/>
                                               	<td><c:out value="${size} Kb"/></td> --%>
                                                <td>
                                                    <%-- <a class="btn btn-primary" href="${pageContext.request.contextPath}/get/${dataFile.stored_file_name}">
                                                    <span class="glyphicon glyphicon-download"></span> Download
                                                    </a>  --%>                                                                                                                                                   
                                                    <a class="btn btn-primary" onclick="fileFunction('${dataFile.stored_file_name}','download')" >
                                                    <span class="glyphicon glyphicon-download"></span> Down
                                                    </a> 
                                                </td>
                                                <td>
                                               		<a class="btn btn-danger" onclick="fileFunction('${dataFile.stored_file_name}','delete')" >
                                                    <span class="glyphicon glyphicon-trash"></span> Del
                                                    </a>
                                                </td>     
                                         </tr>
                                  </c:forEach>
                           </tbody>
                     </table>
              </div>
              
                <a class="btn btn-primary" onclick="multiFileFunction('multiFileDownload')" >
                	<span class="glyphicon glyphicon-download"></span> Download</a>
                <a class="btn btn-danger" onclick="multiFileFunction('multiFileDelete')" >
                	<span class="glyphicon glyphicon-trash"></span> Delete</a>                           
       </div>

       <script type="text/javascript"
              src='<c:url value="js/jquery-2.1.1.js"/>'>
       </script>
       <script type="text/javascript"
              src='<c:url value="bootstrap/js/bootstrap.min.js"/>'>
       </script>
</body>

</html>