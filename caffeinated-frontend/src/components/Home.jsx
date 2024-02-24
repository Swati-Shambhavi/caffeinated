import React from 'react'
import { Link } from 'react-router-dom'
import { FaMugHot, FaPalette, FaCube } from 'react-icons/fa'
import EmbedGenarativeAi from './EmbedGenarativeAi'
import Categories from './pages/Categories'

const Home = () => {
  return (
    <>
      <div className='text-amber-50 relative overflow-hidden h-screen'>
        <video
          autoPlay
          loop
          muted
          className='absolute inset-0 w-full h-full object-cover'
          poster='/path/to/poster-image.jpg'
        >
          <source src='/videos/videobg.mp4' type='video/mp4' />
          Your browser does not support the video tag.
        </video>
        <div
          className='absolute inset-0 bg-black opacity-70 filter blur-md'
          style={{ zIndex: 1 }}
        ></div>
        <div className='flex flex-col justify-center items-center h-full relative z-10'>
          <h1 className='text-2xl md:text-2xl lg:text-3xl xl:text-5xl mb-1 font-kalnia  font-extrabold'>
            Brew Haven.
          </h1>
          <p className='text-md md:text-l lg:text-xl xl:text-2xl mb-3'>
            Your daily escape to the finest coffee creations and cozy blends.
          </p>
          <Link to='/categories'>
            <button className='bg-amber-50 text-black rounded-xl flex items-center justify-center p-2 w-40 hover:bg-black hover:text-amber-50 transition-all duration-300'>
              Shop now
            </button>
          </Link>
        </div>
      </div>
      <div className='flex flex-wrap bg-amber-50 p-8'>
        <section className='w-full sm:w-1/2 md:w-1/2 lg:w-1/3 xl:w-1/3 p-4'>
          <div className='flex items-center'>
            <div>
              <FaPalette size={28} />
            </div>
            <h2 className='ml-2 font-kalnia text-2xl'>Exquisitely curated.</h2>
          </div>
          <p className='pr-3 font-sans'>
            Indulge in flavors from around the world, meticulously crafted in
            your neighborhood for a delightful experience.
          </p>
        </section>
        <section className='w-full sm:w-1/2 md:w-1/2 lg:w-1/3 xl:w-1/3 p-4'>
          <div className='flex items-center'>
            <div>
              <FaMugHot size={28} />
            </div>
            <h2 className='ml-2 font-kalnia text-2xl'>Contemporary Coffee.</h2>
          </div>
          <p className='pr-3 font-sans'>
            Embark on a journey of modern coffee, where every sip is a
            celebration of ethical and holistic brewing methods.
          </p>
        </section>
        <section className='w-full sm:w-1/2 md:w-1/2 lg:w-1/3 xl:w-1/3 p-4'>
          <div className='flex items-center'>
            <div>
              <FaCube size={28} />
            </div>
            <h2 className='ml-2 font-kalnia text-2xl'>Distinctive brews.</h2>
          </div>
          <p className='pr-3 font-sans'>
            Discover our sixteen distinct blends, each house offering a unique
            and unparalleled coffee experience. No two cups are the same!
          </p>
        </section>
        <Categories style='mt-2' />
        <EmbedGenarativeAi />
      </div>
      {/* <div>
        
        <EmbedGenarativeAi />
      </div> */}
    </>
  )
}

export default Home
