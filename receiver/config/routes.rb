ActionController::Routing::Routes.draw do |map|
  # The priority is based upon order of creation: first created -> highest priority.

  map.resources :cos_events, :only => [:create] do |event|
    event.resource :cos_event
  end
  
  map.resources :service_events, :only => [:create] do |event|
    event.resource :service_event
  end

end
