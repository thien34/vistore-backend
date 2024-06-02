import HeaderRight from './HeaderRight'
import Logo from './Logo'
import LogoIcon from '@/assets/logo.svg'

const Header = () => {
    return (
        <div className='border-b'>
            <header className='mx-auto px-4 max-w-screen-xl py-4'>
                <div className='flex items-center justify-between gap-16'>
                    <Logo src={LogoIcon} />
                    <HeaderRight />
                </div>
            </header>
        </div>
    )
}

export default Header
