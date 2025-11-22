//員工介面-搜尋會員
function showorders() {
	$.ajax({
		url:"http://localhost:8080/searchorder",
		type:"get",
		data:{
			member_no:$("#memberid_od").val(),
			member_name:$("#membername_od").val(),
			member_idnumber:$("#idnumber_od").val(),
			member_phone:$("#phone_od").val(),
			pickup_location:$("#pickupsta_od").val(),
			pickup_date:$("#pickupdatestart_od").val(),
			pickup_date_start:$("#pickupdatestart_od").val(),
			pickup_date_end:$("#pickupdateend_od").val(),
			status:$("#odstatus_od").val()
		},
		dataType:"json",
		success:(data) => {
		       fillorder(data)
		   },
		   error:(error) => {
		       alert("error : " + error)
		   }
	})
}

//員工介面-搜尋會員後列表
function fillorder(data){
     let orderTable=$("#allorderTable");
     orderTable.empty();
     data.forEach(ood => {
         let row =
         `<tr>
             <td>${ood.orderNo}</td>
			 <td>${ood.memberNo}</td>
			 <td>${ood.memberName}</td>
			 <td>${ood.pickupDate}</td>
			 <td>${ood.returnDate}</td>
			 <td>${ood.createdAt}</td>
			 <td>${ood.pickupLocation}</td>
			 <td>${ood.returnLocation}</td>
			 <td>${ood.totalAmount}</td>
			 <td>${ood.status}</td>			 
             <td>
                 <a href="#" onclick="editOrder('${ood.orderNo}')">Edit</a>
             </td>
         </tr>`
         orderTable.append(row);
     });
}

window.addEventListener("DOMContentLoaded", () => {
    allbranchname()
});

function allbranchname() {
	$("#pickupsta_od").empty().append('<option value="">請選擇分店</option>');
	$.ajax({
		url:"http://localhost:8080/allbranch",
		type:"get",
		dataType:"json",
		success: (list) => {
		    list.forEach(s => {
		        $("#pickupsta_od").append(`<option value="${s}">${s}</option>`);
		    });
		}
	})
}




//------------------跳轉-------------------//

function gosearchmember(){
		window.location.href="membermanagement.html";
}

function gosearchorder(){
		window.location.href="ordermanagement.html";
}

function start2(){
	$("#searchorder").click(showorders);
	$("#searchmember-emp").click(gosearchmember);
	$("#searchorder-emp").click(gosearchorder);
}

$(document).ready(start2);