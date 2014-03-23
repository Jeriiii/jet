<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
	<head>
		<title>Sample Form</title>
	</head>
	<body>
		<div id="container">

			<h2>Subscribe to The Newsletter!</h2>
			<c:if test="${not empty message}"><div class="message green">${message}</div></c:if>

			<form:form method="post" enctype="multipart/form-data"  
					   modelAttribute="uploadedFile">  
				<table>  
					<tr>  
						<td>Upload File: </td>  
						<td><input type="file" name="file" />  
						</td>  
						<td style="color: red; font-style: italic;"><form:errors  
								path="file" />  
						</td>  
					</tr>  
					<tr>  
						<td> </td>  
						<td><input type="submit" value="Upload" />  
						</td>  
						<td> </td>  
					</tr>  
				</table>  
			</form:form>  
			
		</div>

	</body>
</html>