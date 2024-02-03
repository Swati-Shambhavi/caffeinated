import React from 'react'
import { Link } from 'react-router-dom'
const Home = () => {
  return (
    <div><h2>Welcome to the Caffeinated Wonderland!</h2>
    <p>Come explore our Wonderland with us</p>
    <div>
    <Link to="/categories">categories</Link><br/>
    <Link to="/products">products</Link>
    </div>
    </div>
  )
}

export default Home