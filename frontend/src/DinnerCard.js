import React, { Component } from "react";
import SimpleCard from "./SimpleCard";

class DinnerCard extends Component {

  constructor(props) {
    super(props);

    this.state = {
      dinner: props.value,
    };

    this.handleAcceptedClick = () => {
      this.props.onRSVPClick(this.props.value, true)
    }
    this.handleDeniedClick = () => {
      this.props.onRSVPClick(this.props.value, false)
    }
  }

  render() {
    return (
      <div className="col">
        <SimpleCard
          value={this.state.dinner}
          acceptedClick={this.handleAcceptedClick}
          deniedClick={this.handleDeniedClick}
          status={this.props.statusForCurrentUser}
        />
      </div>
    );
  }
}

export default DinnerCard;