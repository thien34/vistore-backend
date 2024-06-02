import Columns from '@/layouts/components/columns'
import Logo from '@/layouts/components/header/Logo'
import LogoIcon from '@/assets/logo-light.svg'
import { Link } from 'react-router-dom'

const introduce = {
    name: 'Introduce',
    logo: LogoIcon,
    description: 'We are a residential interior design firm located in Portland. Our boutique-studio offer more than'
}

const dataFooter = [
    {
        id: 1,
        name: 'Services',
        childrens: [
            {
                id: 1,
                name: 'Bonus program',
                path: '/bonus'
            },
            {
                id: 2,
                name: 'Interior design',
                path: '/interior'
            },
            {
                id: 3,
                name: 'Residential design',
                path: '/residential'
            }
        ]
    },
    {
        id: 2,
        name: 'Company',
        childrens: [
            {
                id: 1,
                name: 'About',
                path: '/about'
            },
            {
                id: 2,
                name: 'Contact us',
                path: '/contact'
            }
        ]
    },
    {
        id: 3,
        name: 'Assistance to the buyer',
        childrens: [
            {
                id: 1,
                name: 'About',
                path: '/about'
            },
            {
                id: 2,
                name: 'Contact us',
                path: '/contact'
            }
        ]
    }
]

const Footer = () => {
    return (
        <footer className='bg-primary text-primary-foreground'>
            <div className='mx-auto max-w-screen-xl py-16 px-4'>
                <Columns>
                    <>
                        <div className='col-span-4'>
                            <Logo src={introduce.logo} alt={introduce.title} />
                            <p className='mt-3 font-normal leading-6 text-[#CFCFCF]'>{introduce.description}</p>
                        </div>
                        <div className='col-span-2'></div>
                        <>
                            {dataFooter.map((item, index) => (
                                <div key={index} className='col-span-2'>
                                    <p className='font-medium text-lg'>{item.name}</p>
                                    <ul className='mt-3'>
                                        {item.childrens.map((item, index) => (
                                            <li key={index} className='mt-3 font-normal leading-6 text-[#CFCFCF]'>
                                                <Link to={item.path} className='hover:underline'>
                                                    {item.name}
                                                </Link>
                                            </li>
                                        ))}
                                    </ul>
                                </div>
                            ))}
                        </>
                    </>
                </Columns>
            </div>
        </footer>
    )
}

export default Footer
