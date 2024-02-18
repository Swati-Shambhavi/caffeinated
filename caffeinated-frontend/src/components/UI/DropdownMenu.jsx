import React from 'react';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';

const DropdownMenu = () => {
  const {data} = useSelector(state => state.categories);

  return (
    <div className="dropdown-content bg-red">
      {data.map(category => (
        <Link key={category.id} to={`/categories/${category.id}`} className="category-link">
          {category.name}
        </Link>
      ))}
    </div>
  );
};

export default DropdownMenu;