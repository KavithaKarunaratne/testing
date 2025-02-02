import React, {useState, useEffect} from "react";
import {useNavigate} from "react-router-dom";

const HomePage = () => {
  const navigate = useNavigate();
  const [isStarted, setIsStarted] = useState(false);

  useEffect(() => {
    const systemStatus = localStorage.getItem("systemStatus");
    setIsStarted(systemStatus === "started");
  }, []);

  const handleRouting = (path) => {
    switch (path) {
      case "vendorRegister":
        navigate("./vendor-register");
        break;
      case "customerRegister":
        navigate("./customer-register");
        break;
      case "vendorLogin":
        navigate("./vendor-login");
        break;
      case "customerLogin":
        navigate("./customer-login");
        break;
      case "createEvent":
        navigate("./create-event");
        break;
      default:
        break;
    }
  };

  return (
    <div className="min-h-screen bg-purple-50 flex flex-col items-center justify-center p-6">
      <div className="relative z-10 text-center"></div>
      {isStarted ? (
        <>
          {/* Header Section */}
          <div className="fixed left-0 top-0 w-full flex justify-end space-x-6 pr-3">
            <button
              className="text-gray-700 text-lg font-medium hover:underline"
              onClick={() => handleRouting("vendorRegister")}
            >
              Vendor Registration
            </button>
            <button
              className="text-gray-700 text-lg font-medium hover:underline"
              onClick={() => handleRouting("customerRegister")}
            >
              Customer Registration
            </button>
          </div>

          {/* Main Section */}
          <div className="mt-10 flex flex-col items-center space-y-6">
            <button
              className="bg-purple-500 text-white py-3 px-6 rounded-lg text-xl font-semibold shadow hover:bg-purple-600"
              onClick={() => handleRouting("vendorLogin")}
            >
              Vendor Login
            </button>
            <button
              className="bg-purple-500 text-white py-3 px-6 rounded-lg text-xl font-semibold shadow hover:bg-purple-600"
              onClick={() => handleRouting("customerLogin")}
            >
              Customer Login
            </button>
          </div>
        </>
      ) : (
        <p className="text-gray-50 text-xl">The system is not started yet.</p>
      )}

      <div>
        <button
          className="bg-purple-500 text-white py-3 px-6 rounded-lg text-xl font-semibold shadow hover:bg-purple-600 mt-6"
          onClick={() => handleRouting("createEvent")}
        >
          Create Event
        </button>
      </div>
    </div>
  );
};

export default HomePage;
