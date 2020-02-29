<?php
    require_once ('Config.php');
	$response = array();
	$userType=$_POST['userType'];
	$Id=$_POST['userId'];
	$name=$_POST['userName'];
	$email=$_POST['email'];
	$mobile=$_POST['mobile'];
	$pass=$_POST['pass'];

	if($userType=="Faculty")
	{
		$s="select * from faculty where Id= '$Id'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
		{
			$response["status"] = "False";
			$response["message"] = "Faculty ID already exist";
		}
		else
		{
			$reg="insert into faculty values('$Id','$pass','$name','$mobile','$email')";
			mysqli_query($con,$reg);
			$response["status"] = "True";
			$response["message"] = "Registration successfully";
		}
	}
	else
	{
		$s="select * from students where Roll_Number= '$Id'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
		{
			$response["status"] = "False";
			$response["message"] = "Roll Number already exist";
		}
		else
		{
			$reg="insert into students values('$Id','$pass','$name','$mobile','$email')";
			mysqli_query($con,$reg);
			$response["status"] = "True";
			$response["message"] = "Registration successfully";	
		}
	}
	echo json_encode($response);
?>