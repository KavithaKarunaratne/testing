import React, {useState} from "react";
import HomeButton from "./HomeButton";

const EventForm = () => {
  const [eventData, setEventData] = useState({
    maxTicketCapacity: "",
    customerRetrievalRate: "",
    ticketReleaseRate: "",
    totalTickets: "",
  });

  const [notification, setNotification] = useState({type: "", message: ""});

  const [isStarted, setIsStarted] = useState(
    localStorage.getItem("systemStatus") === "started"
  );

  const toggleSystemStatus = () => {
    const newStatus = isStarted ? "stopped" : "started";
    setIsStarted(!isStarted);
    localStorage.setItem("systemStatus", newStatus); // Save status in localStorage
  };

  const handleChange = (e) => {
    const {name, value} = e.target;
    setEventData({...eventData, [name]: value});
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/event/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(eventData),
      });

      if (response.ok) {
        const message = await response.text();
        if (message.includes("saved")) {
          setNotification({type: "success", message}); // Show success notification
        } else {
          setNotification({type: "error", message});
        }
      } else {
        const error = await response.text();
        setNotification({
          type: "error",
          message: `Failed to create event: ${error}`,
        }); // Show error notification
      }
    } catch (error) {
      console.error("Error creating event:", error);
      setNotification({
        type: "error",
        message: "An unexpected error occurred.",
      });
    }

    // Clear the notification after 5 seconds
    setTimeout(() => {
      setNotification({type: "", message: ""});
    }, 5000);
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-purple-50 relative">
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
      <HomeButton />
      <button
        onClick={toggleSystemStatus}
        className={`absolute top-6 right-8 px-3 mb-6 py-3 text-white rounded-md ${
          isStarted
            ? "bg-red-500 hover:bg-red-600"
            : "bg-green-500 hover:bg-green-600"
        }`}
      >
        {isStarted ? "Stop System" : "Start System"}
      </button>

      <form className="w-full max-w-md p-6 " onSubmit={handleSubmit}>
        <h2 className="text-2xl font-bold mb-4 text-center">Create Event</h2>

        <div className="mb-4">
          <label
            htmlFor="maxTicketCapacity"
            className="block text-gray-700 font-medium mb-2"
          >
            Max Ticket Capacity
          </label>
          <input
            type="number"
            id="maxTicketCapacity"
            name="maxTicketCapacity"
            value={eventData.maxTicketCapacity}
            onChange={handleChange}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            required
          />
        </div>

        <div className="mb-4">
          <label
            htmlFor="customerRetrievalRate"
            className="block text-gray-700 font-medium mb-2"
          >
            Customer Retrieval Rate
          </label>
          <input
            type="number"
            id="customerRetrievalRate"
            name="customerRetrievalRate"
            value={eventData.customerRetrievalRate}
            onChange={handleChange}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            required
          />
        </div>

        <div className="mb-4">
          <label
            htmlFor="ticketReleaseRate"
            className="block text-gray-700 font-medium mb-2"
          >
            Vendor Release Rate
          </label>
          <input
            type="number"
            id="ticketReleaseRate"
            name="ticketReleaseRate"
            value={eventData.ticketReleaseRate}
            onChange={handleChange}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            required
          />
        </div>

        <div className="mb-4">
          <label
            htmlFor="totalTickets"
            className="block text-gray-700 font-medium mb-2"
          >
            Total Tickets
          </label>
          <input
            type="number"
            id="totalTickets"
            name="totalTickets"
            value={eventData.totalTickets}
            onChange={handleChange}
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring focus:ring-blue-200"
            required
          />
        </div>

        <button
          type="submit"
          className="w-full bg-purple-500 text-white py-2 rounded-md hover:bg-purple-600 focus:ring focus:ring-purple-300"
        >
          Create Event
        </button>
      </form>
    </div>
  );
};

export default EventForm;
