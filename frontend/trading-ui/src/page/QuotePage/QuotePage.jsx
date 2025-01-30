import React from "react";
import "./QuotePage.scss";
import QuoteList from "../../component/QuoteList/QuoteList";
import { useState, useEffect } from "react";
import axios from "axios";
import { dailyListQuotesUrl } from "../../util/constants";
import NavBar from "../../component/NavBar/NavBar";

function QuotePage(props) {
  const [state, setState] = useState({
    quotes: [],
  });

  const getQuotes = async () => {
    const response = await axios.get(dailyListQuotesUrl);
    if (response && response.data) {
      setState({
        ...state,
        quotes: response.data,
      });
    }
  };

  useEffect(() => {
    getQuotes();
  }, []);

  return (
    <div className="quote-page">
      <NavBar />
      <div className="quotepage-content">
        <div className="title">Quotes</div>
        <QuoteList quotes={state.quotes} />
      </div>
    </div>
  );
}

export default QuotePage;
