set :application, "ressi"
set :repository,  "git://github.com/sizzlelab/ressi.git"

set :scm, :git
set :user, "cos"  # The server's user for deploys
ssh_options[:forward_agent] = true

set :deploy_via, :remote_cache
set :deploy_to, "/var/datat/cos/ressi"

set :server_name, "alpha"
set :host, "alpha.sizl.org"
set :branch, ENV['BRANCH'] || "master"

role :app, host
role :web, host
role :db, host, :primary => true

set :rails_env, :production
set :use_sudo, false

# If you are using Passenger mod_rails uncomment this:
# if you're still using the script/reapear helper you will need
# these http://github.com/rails/irs_process_scripts

namespace :deploy do
  task :start do ; end
  task :stop do ; end
  task :restart, :roles => :app, :except => { :no_release => true } do
    run "#{try_sudo} touch #{File.join(current_path,'receiver/tmp','restart.txt')}"
  end
  
  task :symlinks_to_shared_path do
    run "ln -nfs #{shared_path}/system/database.yml #{release_path}/receiver/config/database.yml"
    run "ln -nfs #{shared_path}/log #{release_path}/receiver/log"
    
  end
  
end

after "deploy:update_code" do
  deploy.symlinks_to_shared_path
end