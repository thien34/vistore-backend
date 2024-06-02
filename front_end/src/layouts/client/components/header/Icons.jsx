import { ShoppingCart } from 'lucide-react'
import Search from './Search'
import Account from './Account'
import { Link } from 'react-router-dom'

const Icons = () => {
    return (
        <div className='flex items-center gap-2 hover:*:bg-[#F5F5F5] *:rounded-md *:p-1 *:cursor-pointer *:transition-all *:size-8'>
            <div>
                <Search />
            </div>
            <div>
                <Link to='/cart'>
                    <ShoppingCart strokeWidth={1.5} />
                </Link>
            </div>
            <div>
                <Account />
            </div>
        </div>
    )
}

export default Icons
