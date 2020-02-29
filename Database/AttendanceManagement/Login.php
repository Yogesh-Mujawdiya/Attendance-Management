<?php
    require_once ('Config.php');
	$response = array();
	$userType=$_POST['userType'];
	$id=$_POST['userId'];
	$pass=$_POST['password'];
	if($userType=="Faculty")
	{
		$s="select * from faculty where Id= '$id' && Password='$pass'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
		{			
		    $r = $result->fetch_assoc();
			$response['Name']=$r["Name"];
			$response['Mobile']=$r["Mobile_Number"];
			$response['Email']=$r["Email"];
			$response["status"] = "True";
			$response["message"] = "Login Successfully";
		} 
		else
		{
			$response["status"] = "False";
			$response["message"] = "invalid information";
		}
	}
	else
	{
		$s="select * from students where Roll_Number= '$id' && Password='$pass'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
	   	{
		    $r = $result->fetch_assoc();
			$response['Name']=$r["Name"];
			$response['Mobile']=$r["Mobile_No"];
			$response['Email']=$r["Email"];
			$response["status"] = "True";
			$response["message"] = "Login Successfully";
	   	}
		else
		{
			$response["status"] = "False";
			$response["message"] = "Invalid Information";
		}		
	}
	echo json_encode($response);
?>