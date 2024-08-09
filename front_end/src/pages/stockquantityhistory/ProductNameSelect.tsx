import React from 'react';
import { Select, Form } from 'antd';
import { SelectProps } from 'antd/es/select';

const { Option } = Select;
export interface ProductSelect {
    id: number;
    name: string;
}
interface ProductSelectProps {
    data: ProductSelect[];
    onChange: (id: number | null) => void;
}
export const ProductSelect: React.FC<ProductSelectProps> = ({ data, onChange }) => {
    const handleChange: SelectProps<number>['onChange'] = (value) => {
      onChange(value);
    }; 
    return (
      <Form.Item  name="productId" label=" Product" >
        <Select
          style={{ width: '300px',height: '40px' }}
          showSearch
          placeholder="Select a product"
          onChange={handleChange}
          filterOption={(input, option) =>
            (option?.children ?? '').toString().toLowerCase().includes(input.toLowerCase())
          }
        >
          <Option value={null}>ALL PRODUCT</Option>
          {data.map((item) => (
            <Option key={item.id} value={item.id}>
              {item.name}
            </Option>
          ))}
        </Select>
      </Form.Item>
    );
  };
  