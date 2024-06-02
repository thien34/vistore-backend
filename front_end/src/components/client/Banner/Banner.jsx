import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel'
import { Link } from 'react-router-dom'

const dataBanner = [
    {
        id: 1,
        image: '/Banner.png'
    },
    {
        id: 2,
        image: '/Banner.png'
    }
]

const Banner = () => {
    return (
        <div className='max-w-screen-2xl mx-auto'>
            <Carousel>
                <CarouselContent>
                    {dataBanner.map((item) => (
                        <CarouselItem key={item.id} className='h-[632px]'>
                            <Link to='/'>
                                <div
                                    style={{
                                        backgroundImage: `url(${item.image})`
                                    }}
                                    className='h-full bg-cover bg-center bg-no-repeat'
                                ></div>
                            </Link>
                        </CarouselItem>
                    ))}
                </CarouselContent>
                <CarouselPrevious className='absolute top-1/2 left-5 -translate-y-1/2' />
                <CarouselNext className='absolute top-1/2 right-5 -translate-y-1/2' />
            </Carousel>
        </div>
    )
}

export default Banner
