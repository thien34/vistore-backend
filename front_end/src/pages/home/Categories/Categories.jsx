import Columns from '@/layouts/components/columns'
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel'
import Category from './Category'
import { Smartphone, Watch, Camera, Headphones, Tv2, Gamepad2, ChevronRight, ChevronLeft } from 'lucide-react'
const dataCaegories = [
    [
        {
            id: 1,
            name: 'Phones',
            path: '/Phones',
            icon: <Smartphone className='size-8' strokeWidth={1.5} />
        },
        {
            id: 2,
            name: 'Watches',
            path: '/Watches',
            icon: <Watch className='size-8' strokeWidth={1.5} />
        },
        {
            id: 3,
            name: 'Cameras',
            path: '/Cameras',
            icon: <Camera className='size-8' strokeWidth={1.5} />
        },
        {
            id: 4,
            name: 'Headphones',
            path: '/Headphones',
            icon: <Headphones className='size-8' strokeWidth={1.5} />
        },
        {
            id: 5,
            name: 'Computers',
            path: '/Computers',
            icon: <Tv2 className='size-8' strokeWidth={1.5} />
        },
        {
            id: 6,
            name: 'Gaming',
            path: '/Gaming',
            icon: <Gamepad2 className='size-8' strokeWidth={1.5} />
        }
    ],
    [
        {
            id: 1,
            name: 'Phones',
            path: '/Phones',
            icon: <Smartphone className='size-8' strokeWidth={1.5} />
        },
        {
            id: 2,
            name: 'Phones',
            path: '/Phones',
            icon: <Smartphone className='size-8' strokeWidth={1.5} />
        },
        {
            id: 3,
            name: 'Phones',
            path: '/Phones',
            icon: <Smartphone className='size-8' strokeWidth={1.5} />
        },
        {
            id: 4,
            name: 'Phones',
            path: '/Phones',
            icon: <Smartphone className='size-8' strokeWidth={1.5} />
        },
        {
            id: 5,
            name: 'Phones',
            path: '/Phones',
            icon: <Smartphone className='size-8' strokeWidth={1.5} />
        },
        {
            id: 6,
            name: 'Phones',
            path: '/Phones',
            icon: <Smartphone className='size-8' strokeWidth={1.5} />
        }
    ]
]
const Categories = () => {
    return (
        <div className='max-w-screen-xl mx-auto px-4 my-20'>
            <Carousel className='select-none'>
                <div className='flex items-center justify-between mb-8'>
                    <h5 className='text-2xl font-medium'>Browse By Category</h5>
                    <div className='relative *:mx-1 *:border-none *:shadow-none '>
                        <CarouselPrevious className='relative top-0 translate-x-0 translate-y-0 left-0'>
                            <ChevronLeft strokeWidth={1.5} />
                        </CarouselPrevious>
                        <CarouselNext className='relative top-0 translate-x-0 translate-y-0 left-0'>
                            <ChevronRight strokeWidth={1.5} />
                        </CarouselNext>
                    </div>
                </div>
                <CarouselContent>
                    {dataCaegories.map((item, index) => (
                        <CarouselItem key={index}>
                            <Columns className='!gap-x-12'>
                                <>
                                    {item.map((category) => (
                                        <Category key={category.id} {...category} />
                                    ))}
                                </>
                            </Columns>
                        </CarouselItem>
                    ))}
                </CarouselContent>
            </Carousel>
        </div>
    )
}

export default Categories
