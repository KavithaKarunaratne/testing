import React, {useState} from "react";
import RealTimeTickets from "./RealTimeTickets";
import {useNavigate} from "react-router-dom";
import HomeButton from "./HomeButton";

const CustomerPurchaseTickets = () => {
  const [ticketsCustomerWant, setTicketsCustomerWant] = useState("");
  const [notification, setNotification] = useState({type: "", message: ""});

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    const customerTicketsPurchaseDTO = {
      ticketsCustomerWant: Number(ticketsCustomerWant),
    };

    try {
      const response = await fetch(
        "http://localhost:8080/api/customer/ticketPurchase",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(customerTicketsPurchaseDTO),
        }
      );

      const message = await response.text(); // Get the string response from the backend

      if (response.ok) {
        setTicketsCustomerWant("");
        setNotification({
          type: "success",
          message, // Show the success message from backend
        });
      } else {
        setNotification({
          type: "error",
          message: `Error: ${message}`, // Show the error message from backend
        });
      }
    } catch (error) {
      console.error("Error:", error);
      setNotification({
        type: "error",
        message: "An unexpected error occurred.",
      });
    }

    // Clear notification after 5 seconds
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
          Ticket: Purchase Tickets
        </h2>

        <div className="mb-4">
          <label
            htmlFor="ticketsCustomerWant"
            className="block text-gray-700 font-medium mb-2"
          >
            Number of Tickets
          </label>
          <input
            type="number"
            id="ticketsCustomerWant"
            value={ticketsCustomerWant}
            onChange={(e) => setTicketsCustomerWant(e.target.value)}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-purple-200"
            required
          />
        </div>

        <button
          type="submit"
          className="w-full bg-purple-500 text-white py-2 rounded-md hover:bg-purple-600 focus:ring focus:ring-purple-300"
        >
          Purchase Tickets
        </button>
      </form>
    </div>
  );
};

export default CustomerPurchaseTickets;
