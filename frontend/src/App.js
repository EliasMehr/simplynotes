import React, {Component} from 'react';
import './assets/app.css';
import {
  Route,
  BrowserRouter,
    Switch
} from "react-router-dom";

import Home from './pages/Home';
import Register from './pages/Register';
import Notes from './pages/Notes';
import NavigationBar from './components/NavigatorBar';
import HeaderJumbo from "./components/HeaderJumbo";
import Layout from "./components/Layout";
import Login from "./pages/Login";
import RegisterLayout from "./components/RegisterLayout";

class App extends Component{


  render() {
    return (
        <React.Fragment>
            <BrowserRouter>
          <NavigationBar/>
          {/*<HeaderJumbo/>*/}
          <Layout>
          <Switch>
            <Route exact path="/" component={Home}/>
            <Route path="/notes" component={Notes}/>
            <Route path="/register" component={Register}/>
            <Route path="/login" component={Login}/>
          </Switch>
          </Layout>
            </BrowserRouter>
        </React.Fragment>
    )
  }

}

export default App;
