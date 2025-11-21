
function gosearchmember(){
		location.href="membermanagement.html";
}

function gosearchorder(){
		location.href="ordermanagement.html";
}

function start(){
     $("#searchmember-emp").click(gosearchmember);
	 $("#searchorder-emp").click(gosearchorder);
}