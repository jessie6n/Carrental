

function fillorder(data){
	$.ajax({
		url:"http://localhost:8080/order/select",
		type:"post",
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
	
     let orderTable=$("#orderTable");
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