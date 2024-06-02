import { Outlet } from 'react-router-dom'
import Header from './components/header'
import Footer from './components/footer'

const LayoutMain = () => {
    return (
        <>
            <Header />
            <Outlet />
            <Footer />
        </>
    )
}

export default LayoutMain
