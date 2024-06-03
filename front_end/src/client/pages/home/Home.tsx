import useAuth from '@/providers/useAuth'
import { Button } from 'antd'

const Home = () => {
    const { setUser } = useAuth()
    return (
        <div className='p-8'>
            page Home
            <br />
            <Button className='mt-4' onClick={() => setUser({ username: 'admin' })}>
                click me private
            </Button>
        </div>
    )
}

export default Home
