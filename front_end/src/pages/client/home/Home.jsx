import Banner from '@/components/client/Banner'
import SmallerBanners from './SmallerBanners'
import Categories from './Categories'
import Products from './Products'
import DiscountProducts from './DiscountProducts'
import Banners from './Banners'
import BannerFoot from './BannerFoot'

function Home() {
    return (
        <div>
            <Banner />
            <SmallerBanners />
            <Categories />
            <Products />
            <Banners />
            <DiscountProducts />
            <BannerFoot bg='https://source.unsplash.com/random' />
        </div>
    )
}

export default Home
