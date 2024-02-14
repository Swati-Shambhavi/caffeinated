import React from 'react'
import Navigation from '../components/Navigation'
import { Outlet } from 'react-router-dom'

const RootLayout = () => {
  return (
    <div className='bg-amber-50'>
      <Navigation/>
      <Outlet/>
    </div>
  )
}

export default RootLayout