function deleteUser(btnObj,email) {
    //var perent=btnObj.parentNode.parentNode;
    var deleteRow=$(btnObj).closest('tr');

    var emailId = email;
    console.log("deleteUser : "+emailId);
    $.ajax({
        type : "POST",
//            contentType : "application/json",
        url : '<c:url value="/removeAccount"/>',
        data: {
            emailId: emailId
        },
//            data : JSON.stringify(data),
        dataType : 'json',
        timeout : 100000,
        success : function(response) {
            console.log("SUCCESS: ", response);
            if(response.resultStatus) {
                alert(response.status+":"+response.resultStatus);
                deleteRow.remove();
            }
            // perent.parentNode.removeChild(perent);

        },
        error : function(e) {
            console.log("ERROR: ", e);
            display(e);
        },
        done : function(e) {
            console.log("DONE");
        }
    });
}

function editUser(btnObj,email) {
//        var updateUserModal = $('#updateUserModal');
}
