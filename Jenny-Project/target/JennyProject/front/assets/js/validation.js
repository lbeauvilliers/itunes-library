
function checkUser(username, password) {
    $.ajax({
        url:"http://localhost:8080/ericssongroupproject/front/rest/users/" + username+"/"+ password,
        type:"GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            var role = data.role;
            switch(role){
                case "System Administrator": window.location = "sa_upload.html";
                    break;
                case "Network Management Engineer": window.location ="nme.html";
                    break;
                case "Customer Service Representative": window.location="csr.html";
                    break;
                case "System Engineer": window.location="se.html";
            }
        },
        error:function () {
            $("#login-error").text("Sorry, wrong username/password combination");
            $("#login-password").val("");
            $("#status-password").removeClass("icon-check");
            $("#status-username").removeClass("icon-check");
        }
    });

}

$(document).ready(function($){
    $("#login-form").submit(function(event){
        event.preventDefault();
    });

    //when user starts entering username
    $("#login-username").on("input", function () {
        var input = $(this);
        var username = input.val();
        if(username){
            $("#status-username").removeClass("icon-close");
            $("#status-username").addClass("icon-check");
            //$("#status-username").switchClass("icon-close", "icon-check");
        }else{
            $("#status-username").removeClass("icon-check");
            $("#status-username").addClass("icon-close");
            $(".login-username").addClass("error-placeholder");
            $("#login-username").addClass("error-input");
        }
    });
    //when user starts entering password
    $("#login-password").on("input", function () {
        var input = $(this);
        var password = input.val();
        if(password){
            $("#status-password").removeClass("icon-close");
            $("#status-password").addClass("icon-check");
            //$("#status-password").switchClass("icon-close", "icon-check");
        }else{
            $("#status-password").removeClass("icon-check");
            $("#status-password").addClass("icon-close");
            $(".login-password").addClass("error-placeholder");
            $("#login-password").addClass("error-input");
        }
    });

    // when user clicks on submit button
    $("#loginButton").click(function() {
        var value_login = $("#login-username").val();
        var value_password = $("#login-password").val();

        // Everything is all right
        if (value_login !== "" && value_login !== null && value_password !== "" && value_password !== null)
        {
            checkUser(value_login, value_password);
        }

        // If its not ok
        else {
            // If login isn't ok
            if (value_login == "")
            {
                $("#status-username").removeClass("icon-check");
                $("#status-username").addClass("icon-close");
                $(".login-username").addClass("error-placeholder");
                $("#login-username").addClass("error-input");
            }

            // If login is ok but password not
            else if (value_login != "")
            {
                $("#status-username").removeClass("icon-close");
                $("#status-username").addClass("icon-check");
                $(".login-username").removeClass("error-placeholder");
                $("#login-username").removeClass("error-input");
            }

            // If password isn't ok
            if (value_password == "")
            {
                $("#status-password").removeClass("icon-check");
                $("#status-password").addClass("icon-close");
                $(".login-password").addClass("error-placeholder");
                $("#login-password").addClass("error-input");
            }

            // If password is ok but login not
            else if (value_password != "")
            {
                $("#status-password").removeClass("icon-close");
                $("#status-password").addClass("icon-check");
                $(".login-password").removeClass("error-placeholder");
                $("#login-password").removeClass("error-input");
            }

            return false;
        }

    });

});