import { useKeycloak } from '@react-keycloak/web';
import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { isAdmin } from '../utility/isAdminUser';
import { ShoppingBagIcon, UserCircleIcon, MagnifyingGlassIcon } from '@heroicons/react/24/outline';
import { FaShoppingCart } from 'react-icons/fa';
import { useSelector } from 'react-redux';

const Navigation = () => {
  const { keycloak } = useKeycloak();
  const navigate = useNavigate();
  const location = useLocation();
  const {data} = useSelector(state => state.cart)

  const handleHomeClick = (event) => {
    event.preventDefault();
    navigate('/');
  };

  const handleLoginClick = (event) => {
    event.preventDefault();
    console.log('Login button clicked');
    keycloak.login();
  };

  const handleLogoutClick = (event) => {
    event.preventDefault();
    console.log('Logout button clicked');
    keycloak.logout();
  };

  const [scrolling, setScrolling] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 0) {
        setScrolling(true);
      } else {
        setScrolling(false);
      }
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  const isHome = location.pathname === '/';

  return (
    <div className={`fixed top-0 left-0 w-full z-10 ${scrolling || !isHome ? 'bg-amber-50 text-black' : 'bg-transparent text-amber-50'}`}>
      <ul className={`flex justify-between font-s ${scrolling || !isHome ? ' border-b border-solid border-black' : ''}`}>
        <li className="hover:cursor-pointer p-5">
          <Link to="/" className="btn-link font-playfair-display font-extrabold text-3xl" onClick={handleHomeClick}>Caffeinated</Link>
        </li>
        <div className="flex space-x-10 p-8">
          <li className="hover:cursor-pointer">
            <Link to="/user/cart" className="btn-link"><MagnifyingGlassIcon className="h-6 w-6" /></Link>
          </li>
          <li className="hover:cursor-pointer flex items-center">
  <Link to="/user/cart" className="btn-link flex items-center">
    <FaShoppingCart className="h-6 w-6" />
    {keycloak && keycloak.authenticated && (
      <span className="ml-2">Cart({ data.cartItems?.length})</span>
    )}
  </Link>
</li>

          {/* {keycloak && keycloak.authenticated && isAdmin() && (
            <li className=" hover:cursor-pointer">
              <Link to="admin/categories" className="btn-link">Admin Dashboard</Link>
            </li>
          )} */}
          {keycloak && !keycloak.authenticated && (
            <li className="hover:cursor-pointer">
              <Link to="/login" className="btn-link" onClick={handleLoginClick}><UserCircleIcon className="h-6 w-6" /></Link>
            </li>
          )}
          {keycloak && keycloak.authenticated && (
            <li className="hover:underline hover:cursor-pointer">
              <Link to="/" className="btn-link" onClick={handleLogoutClick}>Logout ({keycloak.tokenParsed.preferred_username})</Link>
            </li>
          )}
        </div>
      </ul>
    </div>
  );
};

export default Navigation;
