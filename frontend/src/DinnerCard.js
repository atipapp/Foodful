import React, { Component } from "react";
import SimpleCard from "./SimpleCard";

class DinnerCard extends Component {
  render() {
    return (
      <div class="col" id="dinnercard">
        <SimpleCard value={this.getMockDinner()} />
      </div>
    );
  }

  getMockDinner() {
    return {
      title: "Ebedeles",
      location: "Double delight etterem",
      time: "2018/10/27",
      createdBy: "Attila"
    }
  }
}

export default DinnerCard;