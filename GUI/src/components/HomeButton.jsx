import React from "react";
import {Link} from "react-router-dom";

const HomeButton = () => {
  return (
    <div className="absolute top-4 left-4">
      <Link to="/">
        <img
          src="src\assets\home-button-svgrepo-com.svg" // Ensure this path is correct
          alt="Home"
          className="w-10 h-10  hover:bg-gray-200"
        />
      </Link>
    </div>
  );
};

export default HomeButton;
