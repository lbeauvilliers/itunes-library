$(document).ready(function(){

	//to prevent errors in post over localhost
	$("#registerForm").submit(function(event){
		event.preventDefault();
	});	
	
	$("#loginForm").submit(function(event){
		event.preventDefault();
	});	
	
	//--- LOGIN ---//
	
	$("#loginButton").click(function(){
		
		//-- retrieve data from forms --//
		var user_name = $("#loginUsername").val();
		var user_password = $("#loginPassword").val();
		
		$.ajax({
			
			type:"GET",
			url:"rest/base/login",
			data: { user_name: user_name, user_password: user_password },
		    cache: false,
			dataType:"json",
			
			success: function(data) {
				window.location = "dashboard.html?id="+data.library_persistent_id;
            }
            
		});
	});
                  
	//--- REGISTER ---//
	
	$("#registerButton").click(function(){
		
		//-- retrieve data from forms --//
		var user_name = $("#registerUsername").val();
		var library_persistent_id = $("#lpid").val();
		var user_password = $("#registerPassword").val();
		
		//create the user
		var register = function(library_persistent_id,user_name,user_password) {
			this.library_persistent_id=library_persistent_id;
			this.user_name=user_name;
			this.user_password=user_password;
		}
		
		user = new register(library_persistent_id,user_name,user_password);
		
		$.ajax({
			
			type:"POST",
			contentType: "application/json",
			url:"http://localhost:8080/JennyProject/front/rest/base/register",
			data: JSON.stringify(user),
		    cache: false,
			dataType:"json",
			
			success: function() {
				document.getElementById("userCreated").style.display="inline";
				window.location = "login.html#signin";
			}

		});
	});
});	