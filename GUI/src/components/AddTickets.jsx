import React, {useState} from "react";
import RealTimeTickets from "./RealTimeTickets";
import {useNavigate} from "react-router-dom";
import HomeButton from "./HomeButton";

//To store the number of tickets vendor wants to add
const VendorAddTickets = () => {
  const [ticketsVendorHave, setTicketsVendorHave] = useState("");
  // To store notifications for error or confirmation messages
  const [notification, setNotification] = useState({type: "", message: ""});
  //To navigate between routes
  const navigate = useNavigate();
  // control the form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    // The data object that sent to the backend
    const vendorTicketAddDTO = {ticketsVendorHave: Number(ticketsVendorHave)};

    try {
      // Make a POST request for backend to add tickets
      const response = await fetch(
        "http://localhost:8080/api/vendor/ticketAdd",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json", //content type
          },
          body: JSON.stringify(vendorTicketAddDTO), //convert data into json format
        }
      );

      const message = await response.text(); // Get the string response from backend

      if (response.ok) {
        setTicketsVendorHave("");
        setNotification({
          type: "success",
          message, // Show the message from backend
        });
      } else {
        setNotification({
          type: "error",
          message: `Error: ${message}`, //Display the error message from back end
        });
      }
    } catch (error) {
      // Catch and handle any unexpected errors
      console.error("Error:", error);
      setNotification({
        type: "error",
        message: "An unexpected error occurred.",
      });
    }

    //clear notification after 5 seconds
    setTimeout(() => setNotification({type: "", message: ""}), 5000);
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-purple-50">
      {/* Notification Bar */}
      {notification.message && (
        <div
          className={`fixed top-0 left-0 w-full p-4 text-center text-white ${
            notification.type === "success" ? "bg-green-500" : "bg-red-500"
          }`}
        >
          {notification.message}
        </div>
      )}

      <RealTimeTickets />
      <HomeButton />
      <button
        onClick={() => navigate("/customer-delete")}
        className="absolute top-4 right-4 bg-red-500 text-white px-4 py-2 rounded-md hover:bg-red-600 focus:ring focus:ring-red-300"
      >
        Delete My Account
      </button>

      {/* Form */}
      <form className="w-full max-w-md p-6" onSubmit={handleSubmit}>
        <h2 className="text-2xl font-bold mb-4 text-center">
          Vendor: Add Tickets
        </h2>

        <div className="mb-4">
          <label
            htmlFor="ticketsVendorHave"
            className="block text-gray-700 font-medium mb-2"
          >
            Number of Tickets
          </label>
          <input
            type="number"
            id="ticketsVendorHave"
            value={ticketsVendorHave}
            onChange={(e) => setTicketsVendorHave(e.target.value)}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-purple-200"
            required
          />
        </div>

        <button
          type="submit"
          className="w-full bg-purple-500 text-white py-2 rounded-md hover:bg-purple-600 focus:ring focus:ring-purple-300"
        >
          Issue Tickets
        </button>
      </form>
    </div>
  );
};

export default VendorAddTickets;
