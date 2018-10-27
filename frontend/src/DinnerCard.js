import React, { Component } from "react";
import SimpleCard from "./SimpleCard";

class DinnerCard extends Component {

  constructor(props){
    super(props);

    this.state = {
      dinner: props.value,
    };
  }

  render() {
    return (
      <div class="col" id="dinnercard">
        <SimpleCard value={this.state.dinner} />
      </div>
    );
  }
}

export default DinnerCard;