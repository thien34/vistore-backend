import Columns from '@/layouts/client/components/columns'
import ProductBig from './ProductBig'
import ProductTop from './ProductTop'
import ProductButton from './ProductButton'
const productMacbook = {
    name: 'Macbook Air',
    description:
        'The new 15‑inch MacBook Air makes room for more of what you love with a spacious Liquid Retina display.',
    image: '/MacBook Pro 14.png',
    bgColor: '#EDEDED',
    positionImage: 'right',
    textSize: '64'
}
const productPlaystation = {
    name: 'Playstation 5',
    description: 'Incredibly powerful CPUs, GPUs, and an SSD with integrated I/O will redefine your PlayStation',
    image: '/PlayStation.png',
    bgColor: '#fff',
    positionImage: 'left',
    textSize: '56'
}
const productApple = {
    name: 'Apple AirPods Max',
    description: "Computational audio. Listen, it's powerful",
    image: '/airpods-max.png',
    bgColor: '#EDEDED',
    positionImage: 'left',
    textSize: '56'
}
const productVision = {
    name: 'Apple Vision Pro',
    description: "Computational audio. Listen, it's powerful",
    image: '/vision.png ',
    bgColor: '#353535',
    textColor: '#fff',
    positionImage: 'left',
    textSize: '56'
}
const SmallerBanners = () => {
    return (
        <div>
            <Columns className='!gap-0 h-[650px]'>
                <>
                    <div className='col-span-6'>
                        <Columns className='grid-rows-[repeat(2,325px)] !gap-0'>
                            <>
                                <ProductTop product={productPlaystation} bgColor={productPlaystation.bgColor} />
                                <ProductButton product={productApple} bgColor={productApple.bgColor} />
                                <ProductButton
                                    product={productVision}
                                    bgColor={productVision.bgColor}
                                    textColor={productVision.textColor}
                                />
                            </>
                        </Columns>
                    </div>
                    <ProductBig
                        product={productMacbook}
                        bgColor={productMacbook.bgColor}
                        textSize={productMacbook.textSize}
                    />
                </>
            </Columns>
        </div>
    )
}

export default SmallerBanners
