//登入
function loginresult(){
    $.ajax({
        url: "http://localhost:8080/login",
        type: "post",
        data: {
            username: $("#username").val(),
            password: $("#password").val()
        },
        dataType: "json",
        success: (data) => {
            // 正確存 token
            const token = data.token;
            sessionStorage.setItem("jwtToken", token);

            // data.member 才是會員資料
            const member = data.member;

            alert(member.name + " 登入成功");	

            // 若要存會員資料以便其他頁面使用
			sessionStorage.setItem("memberId", member.memberId);
            sessionStorage.setItem("memberName", member.name);
			sessionStorage.setItem("memberIdNumber", member.idNumber);
			sessionStorage.setItem("memberGender", member.gender);
			sessionStorage.setItem("memberEmail", member.email);
			sessionStorage.setItem("memberBirthday", member.birthday);
			sessionStorage.setItem("memberPhone", member.phone);
			sessionStorage.setItem("memberAddress", member.address);
			sessionStorage.setItem("memberRegisterDate", member.registerDate);
			
            location.href = "carrentalDemo.html";
        },
        error: (xhr) => {
            if(xhr.status == 401){
                alert("帳號或密碼錯誤，請重新輸入");
            } else {
                alert("系統錯誤");
            }
        }
    });
}

/*
function gologinout() {
	     if (sessionStorage.getItem("jwtToken")) {
	         // → 已登入 → 做登出
	         sessionStorage.clear();
	         alert("您已登出");
	         location.href = "carrentalDemo.html";
	     } else {
	         // → 未登入 → 導向登入頁
	         location.href = "login.html";
	     }
	 }
*/

//顯示XXX您好
window.onload = function() {
    const token = sessionStorage.getItem("jwtToken");
    const memberName = sessionStorage.getItem("memberName");

    if (token && memberName) {
        // 顯示歡迎訊息
        document.getElementById("welcome-message").innerText =
            memberName + " 您好";
    } else {
        // 沒登入可以顯示預設文字或不顯示
        document.getElementById("welcome-message").innerText = "";
    }
};

//註冊會員
function addmember(e){
	e.preventDefault();
	let form = document.getElementById("registerForm");
	
	let name=$("#membername").val().trim();
	let idnumber=$("#idnumber").val().trim();
	let phone=$("#phone").val().trim();
	let birthday=$("#birthday").val().trim();
	let gender=$("input[name='gender']:checked").val();
	let email=$("#email").val().trim();
	let address=$("#address").val().trim();
	let registerpw=$("#registerpw").val().trim();
	let checkpw=$("#checkpw").val().trim();
	if(name===""){alert("請輸入姓名"); return;}
	else if(idnumber===""){alert("請輸入身分證字號"); return;}
	else if(phone===""){alert("請輸入手機號碼"); return;}
	else if(birthday===""){alert("請輸入生日"); return;}
	else if(!gender){alert("請輸入性別"); return;}
	else if(email===""){alert("請輸入email"); return;}
	else if(address===""){alert("請輸入地址"); return;}
	else if(registerpw===""){alert("請輸入密碼"); return;}
	else if(checkpw===""){alert("請輸入確認密碼"); return;}
	else if(registerpw !== checkpw){alert("密碼不一致,請重新輸入"); return;}
	else if(!form.checkValidity()){
	    form.reportValidity(); // 顯示 pattern 提示
	    return;
	}
	
	
	let memData={
		name:name,
		idNumber:idnumber,
		phone:phone,
		birthday:birthday,
		gender:gender,
		email:email,
		address:address,
		password:registerpw
	};
		
	$.ajax({
		url:"http://localhost:8080/register",
		type:"post",
		contentType:"application/json",
		data: JSON.stringify(memData),
		success:(data)=>{
			console.log("add Success:"+JSON.stringify(data));
			alert("註冊成功,返回登入頁面");
			location.href="login.html";
		},
		error:function(xhr){
			if(xhr.status===409){
				alert("註冊失敗 "+xhr.responseText);
			} else {
				alert("註冊失敗,請檢查資料是否正確")
			}
		}
	})
}

//修改會員資料-已加入驗證token
async function updatememberinformation(){

	const valid = await checkLoginStatusAsync();
	if (valid) {

	let memData={
		memberId:document.getElementById("showid").innerText,
		email:document.getElementById("updateemail").value.trim(),
		address:document.getElementById("updateaddress").value.trim(),
	};
	
	$.ajax({
		url:"http://localhost:8080/updatemember",
		type:"put",
		contentType:"application/json",
		data: JSON.stringify(memData),
		success:(data)=>{
			alert("資料變更完成");
			location.href="member.html";
		},
		error:function(xhr){
			if(xhr.status===409){
				alert("變更失敗 "+xhr.responseText);
			} else {
				alert("變更失敗,請檢查資料是否正確")
			}
		}
	})
	} else {
	    handleLogout();
	}
}

//修改會員密碼-已加入驗證token
async function updatepassword(){

	const valid = await checkLoginStatusAsync();
	if (valid) {

		let memberId=document.getElementById("showid").innerText;
		let oldpassword=$("#originalpw").val().trim();
		let newpw1=$("#newpw1").val().trim();
		let newpw2=$("#newpw2").val().trim();

		if(oldpassword ===""){alert("請輸入舊密碼"); return;}
		else if(newpw1 ===""){alert("請輸入新密碼"); return;}
		else if(newpw2 ===""){alert("請輸入確認密碼"); return;}
		else if(newpw1 !== newpw2){alert("密碼不一致,請重新輸入"); return;}
			
		$.ajax({
			url:"http://localhost:8080/updatepassword",
			type:"put",
			data:{
				memid:memberId,
				oldpw:oldpassword,
				newpw:newpw1
			},
			success:(data)=>{
				alert("密碼變更完成");
				location.href="member.html";
			},
			error:function(xhr){
				if(xhr.status===400){
					alert("變更失敗 "+xhr.responseText);
				} else {
					alert("變更失敗,請檢查資料是否正確")
				}
			}
		})
	} else {
	    handleLogout();
	}
}

//員工介面-搜尋會員
function searchmember() {
	$.ajax({
		url:"http://localhost:8080/searchmember",
		type:"get",
		data:{
			memberid:$("#searmemberid").val(),
			membername:$("#searname").val(),
			idnumber:$("#searidnumber").val(),
			phone:$("#searphone").val()
		},
		dataType:"json",
		success:(data) => {
		       fill(data)
		   },
		   error:(error) => {
		       alert("error : " + error)
		   }
	})
}

//員工介面-搜尋會員後列表
function fill(data){
     let memTable=$("#memTable");
     memTable.empty();
     data.forEach(mem => {
         let row =
         `<tr>
             <td>${mem.memberId}</td>
			 <td>${mem.name}</td>
			 <td>${mem.idNumber}</td>
			 <td>${mem.phone}</td>
			 <td>${mem.birthday}</td>
			 <td>${mem.email}</td>
			 <td>${mem.address}</td>
			 <td>${mem.registerDate}</td>
             <td>
                 <a href="#" onclick="editEmployee('${mem.memberId}')">Edit</a>
             </td>
         </tr>`
         memTable.append(row);
     });
}

//檢查使用者是否已登入 (驗證 Token)
function checkLoginStatusAsync() {
    return new Promise((resolve) => {
        const token = sessionStorage.getItem("jwtToken");
        if (!token) {
            resolve(false);
            return;
        }

        $.ajax({
            url: "http://localhost:8080/validate",
            type: "POST",
            headers: { "Authorization": "Bearer " + token },
            success: function(res) {
                resolve(res.valid === true);
            },
            error: function() {
                resolve(false);
            }
        });
    });
}

function handleLogout() {
    sessionStorage.clear();
    alert("請重新登入會員");
    window.location.href = "login.html";
}

//---------------------------------------跳轉---------------------------------------//

//跳轉會員中心頁面-已加入驗證token
async function goMemberCenter() {
    const valid = await checkLoginStatusAsync();
    if (valid) {
        window.location.href = "member.html";
    } else {
        handleLogout();
    }
}

//跳轉我的訂單-已加入驗證token
async function goMyOrder() {
    const valid = await checkLoginStatusAsync();
    if (valid) {
        window.location.href = "myorders.html";
		showmyorder();
    } else {
        handleLogout();
    }
}

//跳轉變更會員資料-已加入驗證token
async function goUpdatedata() {
    const valid = await checkLoginStatusAsync();
    if (valid) {
        window.location.href = "updatememinformation.html";
    } else {
        handleLogout();
    }
}

//跳轉首頁
function goHomepage(){
	location.href="carrentalDemo.html";
}

//跳轉會員註冊頁面
function goRegisterwindow(){
	location.href="register.html";
}

function gologin(){
	location.href='login.html'
}

//---------------------------------------訂單---------------------------------------//
window.addEventListener("DOMContentLoaded", () => {
    showmyorder(); // 這時候 #orderTable 已經存在
});

function showmyorder(){
	const memberId = sessionStorage.getItem("memberId");
	
	$.ajax({
		url:"http://localhost:8080/order/select",
		type:"post",
		data:{memberid: memberId},
		dataType:"json",
		success:(data) => {
			fillorder(data)
		},
		   error:(error) => {
		   alert("error : " + error)
		}
	})
}

function fillorder(data){
	//alert(JSON.stringify(data, null, 2));
			
	let orderTable=$("#orderTable");
	orderTable.empty();
	data.forEach(od => {
	let row =
		`<tr>
		<td>${od.orderNo}</td>
		<td>${od.pickupDate}</td>
		<td>${od.returnDate}</td>
		<td>${od.createdAt}</td>
		<td>${od.pickupLocation}</td>
		<td>${od.returnLocation}</td>
		<td>${od.status}</td>
		</tr>`
		orderTable.append(row);
	});
}

function start(){
     $("#login").click(loginresult);
	 $("#registernew").click(addmember);
	 $("#search").click(searchmember);
	 $("#pwupdate").click(updatepassword);
	 $("#submitupdate").click(updatememberinformation);
	 $("#register").click(goRegisterwindow);
	 $("#myorderbtn").click(goMyOrder);
	 $("#updatebtn").click(goUpdatedata);
	 $("#homepagebtn").click(goHomepage);
	 $("#loginout").click(gologinout);
}

$(document).ready(start);

$(document).ready(function () {

    // 依照 token 自動切換按鈕文字
    if (sessionStorage.getItem("jwtToken")) {
        $("#loginout").text("會員登出");
    } else {
        $("#loginout").text("會員登入/註冊");
    }

    // 點擊事件
    $("#loginout").click(function () {
        if (sessionStorage.getItem("jwtToken")) {
            // 目前登入 → 按登出
            sessionStorage.clear();
            alert("已成功登出");
            location.href = "carrentalDemo.html"; // 返回首頁或你想跳的頁面
        } else {
            // 尚未登入 → 進登入頁
            location.href = "login.html";
        }
    });

});
