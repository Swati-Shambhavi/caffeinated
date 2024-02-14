import React from 'react';
import backgrndImage from '../../public/images/hero2.jpg';

const Home = () => {
  return (
    <div className="text-amber-50 bg-cover bg-center h-screen relative" style={{ backgroundImage: `url(${backgrndImage})`, zIndex: -1 }}>
      <div
        className="absolute inset-0 bg-black opacity-40 filter blur-md"
        style={{ zIndex: 1 }}
      ></div>
      <div className='flex flex-row h-full'>
        <div className='flex flex-col justify-center items-start p-4 w-1/2' style={{ zIndex: 10 }}>
          <h1 className="text-2xl md:text-3xl lg:text-4xl xl:text-6xl font-bold mb-4">Caffeinated</h1>
          <p className="text-lg md:text-xl lg:text-2xl xl:text-3xl">Your One-Stop Brewtique for all Things Coffee</p>
        </div>
        <div className='w-1/2'></div>
      </div>
    </div>
  );
};

export default Home;
