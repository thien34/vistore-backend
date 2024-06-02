import { NavLink } from 'react-router-dom'

const dataNav = [
    {
        name: 'Home',
        path: '/'
    },
    {
        name: 'About',
        path: '/about'
    },
    {
        name: 'Contact Us',
        path: '/contact'
    }
    // {
    //     name: 'Blog',
    //     path: '/blog'
    // }
]

const Navbar = () => {
    return (
        <ul className='flex items-center gap-8'>
            {dataNav.map((item, index) => {
                return (
                    <li key={index}>
                        <NavLink
                            className={({ isActive }) =>
                                isActive ? 'text-primary' : 'text-[#989898] hover:text-primary transition-all'
                            }
                            to={item.path}
                        >
                            {item.name}
                        </NavLink>
                    </li>
                )
            })}
        </ul>
    )
}

export default Navbar
