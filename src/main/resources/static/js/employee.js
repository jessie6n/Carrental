
//員工登入
function emploginresult(){
	$.ajax({
	    url: "http://localhost:8080/emplogin",
	    type: "POST",
	    data: {
	        empId: $("#empId").val().trim(),
	        empPw: $("#empPw").val().trim()
	    },
	    dataType: "json",
	    success: (data) => {
	        alert(data.empId + " 登入成功");	
	        location.href = "/employee.html";
	    },
	    error: (xhr) => {
	        if(xhr.status == 401){
	            const err = xhr.responseJSON ? xhr.responseJSON.error : "帳號或密碼錯誤";
	            alert(err);
	        } else {
	            alert("系統錯誤");
	        }
	    }
	});
}

/* =========================================
   2. 登出邏輯
   ========================================= */
function emphandleLogout() {
    sessionStorage.clear();
    alert("已登出");
    // ⭐ 修改：回到前端首頁
    window.location.href = "emplogin.html";
}

//員工介面-搜尋訂單
function showorders() {
	$.ajax({
		url:"http://localhost:8080/searchorder",
		type:"get",
		data:{
			member_no: $("#memberid_od").val() || null,
			member_name: $("#membername_od").val() || null,
			member_idnumber: $("#idnumber_od").val() || null,
			member_phone: $("#phone_od").val() || null,
			pickup_location: $("#pickupsta_od").val() || null,
			pickup_date_start: $("#pickupdatestart_od").val() || null,
			pickup_date_end: $("#pickupdateend_od").val() || null,
			status: $("#odstatus_od").val() || null
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

//員工介面-搜尋訂單後列表
function fillorder(data){
    let orderTable = $("#allorderTable");
    orderTable.empty();
    data.forEach(ood => {

        // 只處理 createdAt → 轉台灣時區
        let createTW = new Date(ood.createdAt).toLocaleString("zh-TW", {
            timeZone: "Asia/Taipei",
            hour12: false
        }).replace(/\//g, "-");

        let row =
        `<tr>
            <td><a href="#" onclick="showOrderDetail('${ood.orderNo}')">${ood.orderNo}</a></td>
            <td>${ood.memberNo}</td>
            <td>${ood.memberName}</td>
            <td>${ood.pickupDate}</td>
            <td>${ood.returnDate}</td>
            <td>${createTW}</td>
            <td>${ood.pickupLocation}</td>
            <td>${ood.returnLocation}</td>
            <td>${ood.totalAmount}</td>
            <td>${ood.status}</td>
            <td><a href="#" onclick="showOrderDetail('${ood.orderNo}')">查看明細</a></td>
        </tr>`;

        orderTable.append(row);
    });
}

function showOrderDetail(orderNo){
			window.location.href = `orderDetail.html?orderNo=${orderNo}`;
}

window.addEventListener("DOMContentLoaded", () => {
    allbranchname();
});

function allbranchname() {
	$("#pickupsta_od").empty().append('<option value="">請選擇分店</option>');
	$.ajax({
		url:"http://localhost:8080/allbranchname",
		type:"get",
		dataType:"json",
		success: (list) => {
		    list.forEach(s => {
		        $("#pickupsta_od").append(`<option value="${s}">${s}</option>`);
		    });
		}
	})
}


const urlParams = new URLSearchParams(location.search);
const orderNo = urlParams.get("orderNo");

window.addEventListener("DOMContentLoaded", () => {
    loadOrderDetail(orderNo);
});

// 載入訂單明細
function loadOrderDetail(orderNo) {
	sessionStorage.setItem("editOrderNo", orderNo);

    $.ajax({
        url: `http://localhost:8080/showorder/${orderNo}`,
        type: "get",
		data:{orderNo :orderNo},
        dataType: "json",
        success: (data)=> {
			const days = data.rentalDays;
			const price = data.carPrice;
			const childSeatQty = data.childSeatQty;
			const insuranceQty = data.insurance ? 1 : 0;

			const rentsubtotal = days * price;   // 計算
			//const childSeatAmount = 100 *childSeatQty *days;
			//const insuranceAmount = 400 *insuranceQty *days;
			const childSeatAmount = 200 *childSeatQty;
			const insuranceAmount = 1200 *insuranceQty;
			const addtionalAmount = childSeatAmount+insuranceAmount;
			
			// 兒童座椅
			if (childSeatQty > 0) {
				
			    $("#rowChildSeat").show();
			} else {
			    $("#rowChildSeat").hide();
			}

			// 保險
			if (insuranceQty > 0) {
			    $("#rowInsurance").show();
			} else {
			    $("#rowInsurance").hide();
			}
			
			// 加購項目
			if (addtionalAmount > 0) {
			    $("#rowAdditional").show();
				$("#rowAddtionalsubtotal").show();
			} else {
			    $("#rowAdditional").hide();
				$("#rowAddtionalsubtotal").hide();
			}
			
			
			$("#showmemberName").text(data.memberName);
			$("#showmemberPhone").text(data.memberPhone);
			$("#showmemberEmail").text(data.memberEmail);
			$("#showmemberIdnumber").text(data.memberIdnumber);
			
			$("#showorderNo").text(data.orderNo);
			$("#showcarName").text(data.carName);	
			$("#showpickupLocation").text(data.pickupLocation);
			$("#showreturnLocation").text(data.returnLocation);
			$("#showpickupDate").text(data.pickupDate);
			$("#showpickupTime").text(data.pickupTime);			
			$("#showreturnDate").text(data.returnDate);
			$("#showreturnTime").text(data.returnTime);			
			$("#showrentalDays").text(data.rentalDays);
			$("#showcarPrice").text(data.carPrice);		
			$("#showstatus").text(data.status);
			$("#rentsubtotal").text(rentsubtotal);

            $("#showchildSeatQty").text(childSeatAmount);
            $("#showinsurance").text(insuranceAmount);
			$("#addtionalsubtotal").text(addtionalAmount);
			
			$("#showtotalAmount").text(data.totalAmount);
        }
    });
}

function goEditPage() {
    window.location.href = "updateorder.html";
}


function showeditorder(){
	window.location.href = "updateorder.html";
}

function goBack() {
    history.back();
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
	$("#emplogin").click(emploginresult);
}

$(document).ready(start2);