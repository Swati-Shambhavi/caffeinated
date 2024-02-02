import React from 'react'
import { Link } from 'react-router-dom'
import AddProductForm from './AddProductForm'

const Home = () => {
  return (
    <div><h2>Welcome to the Caffeinated Wonderland!</h2>
    <p>Come explore our Wonderland with us</p>
    <div>
    <Link to="/categories">categories</Link><br/>
    <Link to="/products/addProduct">products</Link>
    </div>
    </div>
  )
}

export default Home