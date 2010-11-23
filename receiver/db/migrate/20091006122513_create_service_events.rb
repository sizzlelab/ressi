class CreateServiceEvents < ActiveRecord::Migration
  def self.up
    create_table :service_events do |t|
      t.string :user_id
      t.string :application_id
      t.string :session_id
      t.string :ip_address
      t.string :action
      t.text :parameters
      t.string :return_value
      t.string :http_user_agent
      t.string :request_uri
      t.string :http_referer
      t.string :semantic_event_id
      
      t.timestamps
    end
  end

  def self.down
    drop_table :service_events
  end
end
