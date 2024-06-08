<?php
include_once"conn.php";
$user_name=$_GET['u_name'];
$user_password=$_GET['u_password'];
$qry="select * from employee_data where username='$user_name' and password='$user_password'";
$result=mysqli_query($conn,$qry);
if(mysqli_num_rows($result)>0){
	echo "success";
}
else{
	echo "login not success";
}
?>