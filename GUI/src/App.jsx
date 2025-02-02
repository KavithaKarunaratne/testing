import React from "react";
import EventForm from "./components/EventConfig";
import VendorAddTickets from "./components/AddTickets";
import CustomerPurchaseTickets from "./components/PurchaseTickets";
import CustomerLogin from "./components/CustomerLogin";
import VendorLogin from "./components/VendorLogin";
import VendorRegister from "./components/VendorRegister";
import CustomerRegister from "./components/CustomerRegister";
import CustomerDelete from "./components/CustomerDelete";
import VendorDelete from "./components/VendorDelete";
import HomePage from "./components/HomePage";
import RealTimeTickets from "./components/RealTimeTickets";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/ws" element={<RealTimeTickets />} />
        <Route path="/customer-register" element={<CustomerRegister />} />
        <Route path="/vendor-register" element={<VendorRegister />} />
        <Route path="/customer-login" element={<CustomerLogin />} />
        <Route path="/vendor-login" element={<VendorLogin />} />
        <Route path="/create-event" element={<EventForm />} />
        <Route path="/add-tickets" element={<VendorAddTickets />} />
        <Route path="/purchase-tickets" element={<CustomerPurchaseTickets />} />
        <Route path="/customer-delete" element={<CustomerDelete />} />
        <Route path="/vendor-delete" element={<VendorDelete />} />
      </Routes>
    </Router>
  );
}

export default App;
