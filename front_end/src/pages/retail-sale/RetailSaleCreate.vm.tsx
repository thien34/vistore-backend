import { useRef, useState } from 'react'
import Bill from './components/Bill'

function useRetailSaleCreateViewModal() {
    type TargetKey = React.MouseEvent | React.KeyboardEvent | string
    const initialItems = [{ label: 'Bill 1', children: <Bill />, key: '1' }]
    const [activeKey, setActiveKey] = useState(initialItems[0].key)
    const [items, setItems] = useState(initialItems)
    const newTabIndex = useRef(0)

    const onChange = (newActiveKey: string) => {
        setActiveKey(newActiveKey)
    }

    const add = () => {
        const newActiveKey = `newTab${newTabIndex.current++}`
        const newPanes = [...items]
        newPanes.push({ label: 'Bill', children: <Bill />, key: newActiveKey })
        setItems(newPanes)
        setActiveKey(newActiveKey)
    }

    const remove = (targetKey: TargetKey) => {
        let newActiveKey = activeKey
        let lastIndex = -1
        items.forEach((item, i) => {
            if (item.key === targetKey) {
                lastIndex = i - 1
            }
        })
        const newPanes = items.filter((item) => item.key !== targetKey)
        if (newPanes.length && newActiveKey === targetKey) {
            if (lastIndex >= 0) {
                newActiveKey = newPanes[lastIndex].key
            } else {
                newActiveKey = newPanes[0].key
            }
        }
        setItems(newPanes)
        setActiveKey(newActiveKey)
    }

    const onEdit = (targetKey: React.MouseEvent | React.KeyboardEvent | string, action: 'add' | 'remove') => {
        if (action === 'add') {
            add()
        } else {
            remove(targetKey)
        }
    }
    return { activeKey, items, onChange, onEdit }
}
export default useRetailSaleCreateViewModal
