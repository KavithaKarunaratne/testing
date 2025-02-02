import {useEffect, useState} from "react";
import React from "react";

const RealTimeTickets = () => {
  const [realTimeTickets, setRealTImeTickets] = useState(0);

  useEffect(() => {
    const eventSource = new EventSource(
      "http://localhost:8080/api/event/stream"
    );

    eventSource.onmessage = (event) => {
      setRealTImeTickets(Number(event.data));
    };

    eventSource.onerror = (event) => {
      console.error("EventSource was not successful.", error);
    };

    return () => {
      eventSource.close();
    };
  }, []);

  return (
    <div className="flex flex-col items-center pb-20 justify-center bg-purple-50">
      <h1 className="text-2xl font-bold mb-4">Current Ticket Pool</h1>
      <div className="text-4xl text-purple-600 font-semibold">
        {realTimeTickets}
      </div>
    </div>
  );
};

export default RealTimeTickets;
