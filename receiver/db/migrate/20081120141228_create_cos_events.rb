class CreateCosEvents < ActiveRecord::Migration
  def self.up
    create_table :cos_events do |t|
      t.string :userid
      t.string :applicationid
      t.string :sessionid
      t.string :address
      t.string :action
      t.string :method
      t.string :parameters
      t.string :headers

      t.timestamps
    end
  end

  def self.down
    drop_table :cos_events
  end
end
