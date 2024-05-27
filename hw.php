<?php
    header("Content-type:text/html;charset=utf-8");
    $servername = "localhost";
    $username = "root";
    $password = "";
    $q1 = $_GET['book1q'];
    $q2 = $_GET['book2q'];
    $q3 = $_GET['book3q'];
    $link = new mysqli($servername, $username, $password);
    if($link->connect_error){
        die("connect failed.");
    }
    $sql = "CREATE DATABASE IF NOT EXISTS orders DEFAULT CHARSET utf8;";
    mysqli_query($link, $sql);
    mysqli_select_db($link, 'orders');
    $sql = "CREATE TABLE IF NOT EXISTS od(order_id INT UNSIGNED AUTO_INCREMENT,b1q INT NOT NULL,b2q INT NOT NULL,b3q INT NOT NULL,PRIMARY KEY (order_id));";
    mysqli_query($link, $sql);
    $sql = "INSERT INTO od (b1q, b2q, b3q) VALUES (" . $q1 . ", " . $q2 . ", " . $q3 . ");";
    mysqli_query($link, $sql);
    $sql="SELECT * FROM od";
    mysqli_select_db($link, 'orders');
    $retval = mysqli_query($link, $sql);
    echo "Thank you for your purchase! <br/>";
    echo "You ordered " . $q1 . " The Adventures of Huckleberry Finn, " . $q2 . " Hamlet and " . $q3 . " War and Peace.";
    echo "<br/> Your order will be deliveried as fast as we can. Have a good day!";
    echo "<br/><br/>**********For testing, print data in the database**********<br/>";
    echo "<table border='1'><tr><td>order_id</td><td>book 1</td><td>book 2</td><td>book 3</td></tr>";
    while($row = mysqli_fetch_array($retval, MYSQLI_ASSOC)){
        echo "<tr><td>{$row['order_id']}</td>" .
        "<td>{$row['b1q']}</td>" .
        "<td>{$row['b2q']}</td>" .
        "<td>{$row['b3q']}</td>" .
        "</tr>";
    }
    echo "</table>";

    $link->close();
?>
