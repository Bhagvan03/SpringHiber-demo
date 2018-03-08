<%@include file="libs.jsp"%>
<html>
<head>
    <title>Admin Page</title>
    <h1>${message}</h1>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jquery.dataTables.min.css'/> "/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.min.css'/> "/>
    <script type="text/javascript" src="<c:url value='/resources/js/jquery/jquery-3.2.0.min.js'/>" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/bootstrap/bootstrap.min.js'/>" ></script>
    <script type="text/javascript" src="<c:url value='/resources/js/jquery-datatable/jquery.dataTables.min.js'/>" ></script>

</head>
<body bgcolor="#fffaf0" style="font-family: Arial">
<div>
    <c:url value="/logout" var="logoutUrl" />
    <form action="${logoutUrl}" method="post" id="logoutForm">
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />
    </form>
    <script>
        function formSubmit() {
          $("#logoutForm").submit();
        }
    </script>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <h2>
            Welcome : ${pageContext.request.userPrincipal.name} |
                <a href="javascript:formSubmit()"> Logout</a>
            <%--<a onclick="formSubmit();" href="javascript:void(0);"> Logout</a>--%>
        </h2>
    </c:if>
</div>


<div class="container">

    <!-- Modal -->
    <div class="modal fade" id="updateFormModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
             <%--<springform:form action="createnewuser" method="post">--%>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <%--<h4 class="modal-title">Update User</h4>--%>
                </div>
                <div class="modal-body">
                  <%-- <springform:form action="createnewuser" method="post">--%>
                    <center>
                        <table border="1" width="60%" cellpadding="4">
                            <thead>
                            <tr>
                                <th colspan="3" align="center" style="text-align:center">Update User</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>USERNAME : </td>
                                <td colspan="2"><input type="text" name="userName" id="userName" placeholder="UserName" /></td>
                            </tr>
                            <tr>
                                <td>EMAIL ADDRESS : </td>
                                <td colspan="2"><input type="text" name="email" id="email" placeholder="Email Address" disabled/><br/></td>
                            </tr>
                            <tr>
                                <td>USER ROLE : </td>
                                <c:forEach items="${roleList}" var="roleList">
                                    <c:if test="${roleList ne 'ROLE_ADMIN'}">
                                        <td><input type="radio" name="role" value="${roleList.getRole()}"/>${roleList.getRole()}</td>
                                    </c:if>
                                </c:forEach>
                            </tr>
                            <tr>
                                <td colspan="3" align="center"><input type="submit" value="Submit" onclick="updateUserAccount()"/></td>
                            </tr>
                            </tbody>
                            <input type="hidden" name="${_csrf.parameterName}"
                                   value="${_csrf.token}" />
                        </table>
                    </center>
                <%--</springform:form>--%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div>
<springform:form action="#" method="post">
    <center>
        <div style="width: 800px; border: 1px solid black; padding: 3px">
         <table id="userDetailsDataTableId">
            <thead>
            <tr>
                <th rowspan="1" colspan="4" style="text-align:center">Admin'S Users Here</th>
            </tr>
            <%--<c:set var="rowCounter" value="0"/>
            <c:forEach items="${userList}" var="userList">
                <c:if test="${userList.getRole() ne 'ROLE_ADMIN' and rowCounter eq 2}">--%>
                    <tr>
                        <td>USERNAME </td>
                        <td>EMAIL ADDRESS </td>
                        <td>USER ROLE </td>
                        <td>USER CORRECTION</td>
                    </tr>

                <%--</c:if>
                <c:set var="rowCounter" value="rowCounter+1"/>
            </c:forEach>--%>
            </thead>
            <tbody id="userListId">

            <%--<c:forEach items="${userList}" var="user">
                <c:if test="${user.getRole() ne 'ROLE_ADMIN'}">
                    <tr>
                        <td>${user.getUserName()}</td>
                        <td>${user.getEmail()}</td>
                        <td>${user.getRole()}</td>
                        <td colspan="2"><input type="button" name="edit" value="editUser" data-toggle="modal" data-target="#updateFormModal" onclick="editUser(this)"/>
                            <input type="button" name="delete" value="deleteUser" onclick="deleteUser(this,'${user.getEmail()}')"/></td>
                    </tr>
                </c:if>
            </c:forEach>--%>
            </tbody>
        </table>
        <tr>
            <td colspan="4" align="center"><a href="registration">CreateUser</a><br/></td>
        </tr>

        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />

        </div>
    </center>
</springform:form>
</div>
<script>
    jQuery.fn.dataTableExt.oApi.fnPagingInfo = function ( oSettings )
    {
        return {
            "iStart":         oSettings._iDisplayStart,
            "iEnd":           oSettings.fnDisplayEnd(),
            "iLength":        oSettings._iDisplayLength,
            "iTotal":         oSettings.fnRecordsTotal(),
            "iFilteredTotal": oSettings.fnRecordsDisplay(),
            "iPage":          oSettings._iDisplayLength === -1 ?
                0 : Math.ceil( oSettings._iDisplayStart / oSettings._iDisplayLength ),
            "iTotalPages":    oSettings._iDisplayLength === -1 ?
                0 : Math.ceil( oSettings.fnRecordsDisplay() / oSettings._iDisplayLength )
        };
    };

    $(document).ready(function() {

        $("#userDetailsDataTableId").dataTable( {
            "processing": true,
            "bServerSide": true,
            "paging": true,
            "ordering": true,
            "info": true,
            "sColumns":true,
            "iSortingCols":true,
            "mDataProp":true,
            "lengthMenu": [ [10, 25, 50, 100, -1], [10, 25, 50, 100, "All"] ],
            "sort": "position",
            "iSortCol":true,
            "sSortDir":true,
            //bStateSave variable you can use to save state on client cookies: set value "true"
            "bStateSave": false,
            //Default: Page display length
            "iDisplayLength": 10,
            //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
            "iDisplayStart": 0,
            "fnDrawCallback": function () {
                //Get page numer on client. Please note: number start from 0 So
                //for the first page you will see 0 second page 1 third page 2...
                //Un-comment below alert to see page number
//                iDisplayStart=this.fnPagingInfo().iPage;
//                alert("Current page number: "+this.fnPagingInfo().iPage);
            },
            "sAjaxSource": "<c:url value="/admin/serverSidePaginationDataTables"/>",
            "sServerMethod":"POST",
            "aoColumns": [
                { "mData": "userName" },
                { "mData": "email" },
                { "mData": "role" },
                {"defaultContent":"<td colspan='2'><input type='button' name='edit' value='editUser' data-toggle='modal' data-target='#updateFormModal' onclick='editUser(this)'/><input type='button' name='delete' value='deleteUser' onclick='deleteUser(this)'/></td>"},
            ]

        } );

    } );



    function editUser(btnObj) {
        var editRow = $(btnObj).closest('tr');
        var role = editRow.find('td:eq(2)').text();
        $("#userName").val(editRow.find('td:eq(0)').text());
        $("#email").val(editRow.find('td:eq(1)').text());
        var roleObject=$('input:radio[name=role]');
        for (var i=0;i< roleObject.length;i++){
            console.log("roleObject : "+roleObject[i].value);
            if (roleObject[i].value==role) {

                roleObject[i].checked = true;
            }
        }
    }


    /***
     * update user account sending data by ajax and updated user details...
     */
    function updateUserAccount() {
        var userName=$("#userName").val();
        var emailId = $("#email").val();
        var role;
        var roleJQueryObject=$('input:radio[name=role]');
        for (var i=0;i< roleJQueryObject.length;i++){

            if (roleJQueryObject[i].checked==true) {
                role=roleJQueryObject[i].value ;
            }
        }
        $.ajax({
            type : "POST",
//            contentType : "application/json",
            url : '<c:url value="/admin/updateAccount"/>',
            data: {
                userName: userName,
                emailId: emailId,
                role : role
            },
//            data : JSON.stringify(data),
            dataType : 'json',
            timeout : 100000,
            success : function(response) {
                console.log("SUCCESS: ", response);
                if(response.resultStatus) {
                    alert(response.status+":"+response.resultStatus);
                    $(".close").click();
                    updateUserAccountDataTable(response);
                }
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
    /**
     *
     *update user particular datatables row
     * @param response
     * */
    function updateUserAccountDataTable(response) {
        var userName=response.resultObject.userName;
        var email=response.resultObject.userEmailId;
        var role=response.resultObject.userRole;
        email=email.trim();
        var editableTD='<td colspan="2"><input type="button" name="edit" value="editUser" data-toggle="modal" data-target="#updateFormModal" onclick="editUser(this)"/><input type="button" name="delete" value="deleteUser" onclick="deleteUser(this)"/></td>';
        var userDetailsForDataTable = [userName, email,role,editableTD];

        var dataTableAccountDetail= $("#userDetailsDataTableId").DataTable();
        $("#userListId").children("tr").each(function(){
             if($(this).find('td:eq(1)').text()==email) {
                 dataTableAccountDetail.row(this).draw(userDetailsForDataTable);
                 /*$(this).find('td:eq(0)').html(userName);
                 $(this).find('td:eq(2)').html(userRole);*/
             }

         });
    }

    /***
     * update user account ending...
     */


    /***
     * delete particular account
     * @param btnObj
     */

    function deleteUser(btnObj) {
        var deleteRow = $(btnObj).closest('tr');
        var emailId = deleteRow.find('td:eq(1)').text();
        console.log("deleteUser : " + emailId);
        $.ajax({
            type: "POST",
            url: '<c:url value="/admin/removeAccount"/>',
            data: {
                emailId: emailId
            },
            dataType: 'json',
            timeout: 100000,
            success: function (response) {
                console.log("SUCCESS: ", response);
                if (response.resultStatus) {
                    $("#userDetailsDataTableId").DataTable().draw();
                  alert(response.status + ":" + response.resultStatus);
                }
            },
            error: function (e) {
                console.log("ERROR: ", e);
                display(e);
            },
            done: function (e) {
                console.log("DONE");
            }
        });
    }
</script>
</body>
</html>
